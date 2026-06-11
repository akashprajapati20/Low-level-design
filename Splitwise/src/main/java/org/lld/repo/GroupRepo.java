package org.lld.repo;

import org.lld.models.Group;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GroupRepo {
    private final Map<String, Group> Groups=new HashMap<>();

    public String addGroup(Group group){
        Groups.put(group.getGroupId(),group);
        return group.getGroupId();
    }

    public Optional<Group>  getGroup(String groupId){
       Group group= Groups.getOrDefault(groupId,null);
       if(group==null)throw new IllegalArgumentException("id not present ");
       return Optional.of(group) ;
    }
}
