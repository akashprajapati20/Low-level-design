struct node {
    int key, val;
    node *prev, *next;
    node(int k, int v) : key(k), val(v), prev(nullptr), next(nullptr) {}
};

class LRUCache {
    unordered_map<int, node*> mp;   // O(1) instead of map's O(log n)
    int cap;
    node* head = new node(-1, -1);
    node* tail = new node(-1, -1);

    void insertn(node* t) {
        node* nxt = head->next;
        head->next = t; t->prev = head;
        t->next = nxt;  nxt->prev = t;
    }
    void deleten(node* t) {
        t->prev->next = t->next;
        t->next->prev = t->prev;
    }

public:
    LRUCache(int capacity) : cap(capacity) {
        head->next = tail;
        tail->prev = head;
    }

    int get(int key) {
        if (mp.find(key) == mp.end()) return -1;
        node* t = mp[key];
        deleten(t); insertn(t);
        return t->val;
    }

    void put(int key, int value) {
        if (mp.find(key) != mp.end()) {
            node* t = mp[key];
            t->val = value;
            deleten(t); insertn(t);
        } else {
            if ((int)mp.size() == cap) {
                node* t = tail->prev;
                mp.erase(t->key);
                deleten(t);
                delete t;
            }
            node* n = new node(key, value);
            mp[key] = n;
            insertn(n);
        }
    }
};