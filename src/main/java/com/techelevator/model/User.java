package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Model class representing an application user.
 *
 * Contains information about the user - their id, username, address information,
 * password (hashed) and authorities (user roles).
 */
public class User {

   private int id;
   private String username;
   private String firstName;
   private String lastName;
   private String email;
   @JsonIgnore
   private String password;
   @JsonIgnore
   private boolean activated;
   private Set<Authority> authorities = new HashSet<>();

   public User() { }


   public User(int id, String username, String password, String authorities, String firstName, String lastName, String email) {
      this.id = id;
      this.username = username;
      this.password = password;
      if(authorities != null) this.setAuthorities(authorities);
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.activated = true;
   }

   public User(String username, String password, String authorities, String firstName, String lastName, String email) {
      this(0, username, password, authorities, firstName, lastName, email);
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public String getAuthoritiesString() {
      String authString = "";
      for (Authority auth : authorities) {
         if (authString.length() == 0) {
            authString += auth.getName();
         } else {
            authString += "," + auth.getName();
         }
      }
      return authString;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for(String role : roles) {
         String authority = role.contains("ROLE_") ? role : "ROLE_" + role.toUpperCase();
         this.authorities.add(new Authority(authority));
      }
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(firstName, user.firstName) &&
              Objects.equals(lastName, user.lastName) &&
              Objects.equals(email, user.email) &&
              Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", first_name='" + firstName + '\'' +
              ", last_name=' " + lastName + '\'' +
              ", email='" + email + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }
}
