package kz.kazgisa.data.dto;

import kz.kazgisa.data.enums.MenuCode;

import java.util.List;

public class MenuCountDto {
    public Long incoming;
    public Long allTasks;
    public Long unassignedTasks;
    public Long outcoming;
    public Long control;
    public Long currentTasks;
    public Long finishedTasks;
    public Long incomingCorrespondences;
    public Long outcomingCorrespondences;
    public List<MenuCode> access;
}
