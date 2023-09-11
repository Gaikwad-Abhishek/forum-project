package com.prodapt.learningspring.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Post {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  
  private String content;
  
  public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public User getAuthor() {
	return author;
}

public void setAuthor(User author) {
	this.author = author;
}

@Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Date createdAt;
  
  @PrePersist
  protected void onCreate() {
      createdAt = new Date();
  }
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private User author;
  
  public boolean isPresent() {

  		return createdAt != null;

  }
  
}
