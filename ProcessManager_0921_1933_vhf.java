// 代码生成时间: 2025-09-21 19:33:44
 * The class is designed to be extensible and maintainable.
 */

package com.example.processmanager;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ProcessManager class that handles process operations.
 */
@ParentPackage("struts-default")
@Namespace("/process")
public class ProcessManager extends ActionSupport {

    // List to hold the process details
    private List<ProcessDetail> processList;

    // Getter and setter for processList
    public List<ProcessDetail> getProcessList() {
        return processList;
    }

    public void setProcessList(List<ProcessDetail> processList) {
        this.processList = processList;
    }

    /**
     * Action to list all processes.
     * @return String
     */
    @Action(value = "listProcesses", results = {
            @Result(name = "success", type = "json")
    })
    public String listProcesses() {
        List<ProcessDetail> processes = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("ps", "-ef");
            Process p = pb.start();
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                processes.add(new ProcessDetail(line));
            }
            input.close();
            p.destroy();
            this.processList = processes;
        } catch (Exception e) {
            // Handle exceptions and set error message
            addActionError("Error listing processes: " + e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Action to start a process.
     * @param command the command to execute as a new process.
     * @return String
     */
    @Action(value = "startProcess")
    public String startProcess(String command) {
        try {
            ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", command);
            pb.start();
            addActionMessage("Process started successfully: " + command);
        } catch (Exception e) {
            // Handle exceptions and set error message
            addActionError("Error starting process: " + e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Action to stop a process.
     * @param processId the ID of the process to stop.
     * @return String
     */
    @Action(value = "stopProcess")
    public String stopProcess(int processId) {
        try {
            ProcessBuilder pb = new ProcessBuilder("kill", Integer.toString(processId));
            pb.start().waitFor();
            addActionMessage("Process stopped successfully: PID " + processId);
        } catch (Exception e) {
            // Handle exceptions and set error message
            addActionError("Error stopping process: " + e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    // Inner class to hold details of a process
    public static class ProcessDetail {
        private String processInfo;

        public ProcessDetail(String info) {
            this.processInfo = info;
        }

        public String getProcessInfo() {
            return processInfo;
        }

        public void setProcessInfo(String processInfo) {
            this.processInfo = processInfo;
        }
    }
}
