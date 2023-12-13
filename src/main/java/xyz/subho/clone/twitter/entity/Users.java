/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2023 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.subho.clone.twitter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
    name = "users",
    indexes = {@Index(columnList = "username")})
@Data
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(unique = true, nullable = false, length = 30)
  private String username;

  @Column(nullable = false, length = 50)
  private String name;

  private String avatar;

  @Column(unique = true)
  private String email;

  @Column(length = 240)
  private String bio;

  @Column(name = "follower_count", columnDefinition = "BIGINT(20) default '0'", nullable = false)
  private long followerCount = 0L;

  @Column(name = "following_count", columnDefinition = "BIGINT(20) default '0'", nullable = false)
  private Long followingCount = 0L;

  @Column(columnDefinition = "boolean default false", nullable = false)
  private Boolean verified = false;

  @CreationTimestamp private Date createdAt;

  @UpdateTimestamp private Date updatedAt;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Likes> userLikes = new ArrayList<>();

  @ElementCollection private Map<UUID, Date> follower = new HashMap<>();

  @ElementCollection private Map<UUID, Date> following = new HashMap<>();

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Posts> userPosts = new ArrayList<>();

  public void setFollower(final UUID userId) {
    follower.put(userId, new Date());
  }

  public void setFollowing(final UUID userId) {
    following.put(userId, new Date());
  }

  public void removeFollower(final UUID userId) {
    follower.remove(userId);
  }

  public void removeFollowing(final UUID userId) {
    following.remove(userId);
  }
}
