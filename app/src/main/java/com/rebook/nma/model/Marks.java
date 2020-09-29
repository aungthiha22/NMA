package com.rebook.nma.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Marks {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mark_id")
    @Expose
    private Integer markId;
    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("batch_id")
    @Expose
    private Integer batchId;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("attendance")
    @Expose
    private Integer attendance;
    @SerializedName("group_discussion")
    @Expose
    private Integer groupDiscussion;
    @SerializedName("group_presentation")
    @Expose
    private Integer groupPresentation;
    @SerializedName("assignment")
    @Expose
    private Integer assignment;
    @SerializedName("exam")
    @Expose
    private Integer exam;
    @SerializedName("final_result")
    @Expose
    private Integer finalResult;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMarkId() {
        return markId;
    }

    public void setMarkId(Integer markId) {
        this.markId = markId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getGroupDiscussion() {
        return groupDiscussion;
    }

    public void setGroupDiscussion(Integer groupDiscussion) {
        this.groupDiscussion = groupDiscussion;
    }

    public Integer getGroupPresentation() {
        return groupPresentation;
    }

    public void setGroupPresentation(Integer groupPresentation) {
        this.groupPresentation = groupPresentation;
    }

    public Integer getAssignment() {
        return assignment;
    }

    public void setAssignment(Integer assignment) {
        this.assignment = assignment;
    }

    public Integer getExam() {
        return exam;
    }

    public void setExam(Integer exam) {
        this.exam = exam;
    }

    public Integer getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(Integer finalResult) {
        this.finalResult = finalResult;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }
}
