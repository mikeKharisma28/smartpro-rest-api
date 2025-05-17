package com.juaracoding.smartpro_rest_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Division", schema = "MasterData")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name", length = 70, unique = true)
    private String name;

    @Column(name = "Url")
    private String url;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CreatedDate", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "UpdatedBy", insertable = false)
    private Long updatedBy;

    @Column(name = "CreatedDate", insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    // columns with foreign key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParentId")
    private Menu parent;

    // members
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> children = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccessPermission> accessPermissions = new ArrayList<>();

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public List<AccessPermission> getAccessPermissions() {
        return accessPermissions;
    }

    public void setAccessPermissions(List<AccessPermission> accessPermissions) {
        this.accessPermissions = accessPermissions;
    }
}
