package com.juaracoding.smartpro_rest_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Menu", schema = "MasterData")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name", length = 70)
    private String name;

    @Column(name = "Path")
    private String path;

    @Column(name = "IconBootstrap")
    private String iconBootstrap;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CreatedDate", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "UpdatedBy", insertable = false)
    private Long updatedBy;

    @Column(name = "UpdatedDate", insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    // columns with foreign key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParentId")
    private Menu parent;

    // members

    // setters getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIconBootstrap() {
        return iconBootstrap;
    }

    public void setIconBootstrap(String iconBootstrap) {
        this.iconBootstrap = iconBootstrap;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

}
