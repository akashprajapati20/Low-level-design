package org.lld;

import java.util.*;

public class ConnectionPool {

    /** Notifies a previously-queued request that it has now been assigned a connection. */
    public interface AssignmentListener {
        void onAssigned(String requestId, int connectionId);
    }

    public static final int QUEUED = -1;

    private final int capacity;
    private final Deque<Integer> free = new ArrayDeque<>();       // available connection ids
    private final Queue<String> waitQueue = new ArrayDeque<>();   // FIFO of waiting requestIds
    private final Map<String, Integer> assignment = new HashMap<>(); // requestId -> connectionId
    private final Set<String> queued = new HashSet<>();
    private final AssignmentListener listener;

    public ConnectionPool(int capacity, AssignmentListener listener) {
        if (capacity < 0) throw new IllegalArgumentException("capacity < 0");
        this.capacity = capacity;
        this.listener = listener;
        for (int i = 0; i < capacity; i++) free.offerLast(i);
    }

    /**
     * Returns the assigned connectionId if one was free, else QUEUED (-1).
     * If queued, the listener fires later when a connection is handed to this request.
     */
    public int requestConnection(String requestId) {
        if (assignment.containsKey(requestId) || queued.contains(requestId))
            throw new IllegalStateException("duplicate active request: " + requestId);

        if (!free.isEmpty()) {
            int connId = free.pollFirst();
            assignment.put(requestId, connId);
            return connId;
        }
        waitQueue.offer(requestId);
        queued.add(requestId);
        return QUEUED;
    }

    /** Releases the connection held by requestId; hands it to the oldest waiter if any. */
    public void releaseConnection(String requestId) {
        Integer connId = assignment.remove(requestId);
        if (connId == null) return;                 // not holding anything -> idempotent no-op

        if (!waitQueue.isEmpty()) {
            String next = waitQueue.poll();
            queued.remove(next);
            assignment.put(next, connId);           // direct hand-off; connection never goes idle
            if (listener != null) listener.onAssigned(next, connId);
        } else {
            free.offerLast(connId);
        }
    }

    // Inspection helpers — useful in tests
    public int availableCount() { return free.size(); }
    public int waitingCount()   { return waitQueue.size(); }
    public int capacity()       { return capacity; }
}