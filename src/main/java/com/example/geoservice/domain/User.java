package com.example.geoservice.domain;

import java.security.Principal;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Principal {

    private Long id;

    private String authToken;

    private String firstName;

    private String lastName;

    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String authToken) {
        this();
        this.authToken = authToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return Sets.immutableEnumSet(roles);
    }

    public void addRoles(Role role, Role... others) {
        this.roles.addAll(EnumSet.of(role, others));
    }

    @Override
    public String getName() {
        return authToken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return Objects.equals(this.id, ((User) obj).id);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                                     .add("id", id)
                                     .add("firstName", firstName)
                                     .add("lastName", lastName)
                                     .add("roles", roles)
                                     .toString();
    }
}
