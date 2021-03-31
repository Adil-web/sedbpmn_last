package kz.kazgisa.data.dto;

import org.camunda.bpm.engine.task.DelegationState;
import org.camunda.bpm.engine.task.Task;

import java.util.Date;
import java.util.Map;

public class TaskDto implements Task {
    private String id;
    private String name;
    private String description;
    private int priority;
    private String owner;
    private String assignee;
    private DelegationState delegationState;
    private String processInstanceId;
    private String executionId;
    private String processDefinitionId;
    private String caseInstanceId;
    private Date createTime;
    private Date dueDate;
    private String caseExecutionId;
    private String caseDefinitionId;
    private Date followUpDate;
    private String delegate;
    private String parentTaskId;
    private boolean suspended;
    private String tenantId;
    private Map<String, Object> content;
    private Date startTime;
    private Date endTime;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String s) {
        this.name = s;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String s) {
        this.description = s;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int i) {
        this.priority = i;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String s) {
        this.owner = s;
    }

    @Override
    public String getAssignee() {
        return assignee;
    }

    @Override
    public void setAssignee(String s) {
        this.assignee = s;
    }

    @Override
    public DelegationState getDelegationState() {
        return delegationState;
    }

    @Override
    public void setDelegationState(DelegationState delegationState) {
        this.delegationState = delegationState;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    @Override
    public String getExecutionId() {
        return executionId;
    }

    @Override
    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    @Override
    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    @Override
    public void setCaseInstanceId(String s) {
        this.caseInstanceId = s;
    }

    @Override
    public String getCaseExecutionId() {
        return caseExecutionId;
    }

    @Override
    public String getCaseDefinitionId() {
        return caseDefinitionId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String getTaskDefinitionKey() {
        return null;
    }

    @Override
    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public void setDueDate(Date date) {
        this.dueDate = date;
    }

    @Override
    public Date getFollowUpDate() {
        return followUpDate;
    }

    @Override
    public void setFollowUpDate(Date date) {
        this.followUpDate = date;
    }

    @Override
    public void delegate(String s) {
        this.delegate=s;
    }

    @Override
    public void setParentTaskId(String s) {
        this.parentTaskId =s;
    }

    @Override
    public String getParentTaskId() {
        return parentTaskId;
    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public String getFormKey() {
        return null;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(String s) {
        this.tenantId = s;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public void setCaseExecutionId(String caseExecutionId) {
        this.caseExecutionId = caseExecutionId;
    }

    public void setCaseDefinitionId(String caseDefinitionId) {
        this.caseDefinitionId = caseDefinitionId;
    }

    public String getDelegate() {
        return delegate;
    }

    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
