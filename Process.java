import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int priority;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

class PriorityScheduling {
    public static void schedulingPriority(List<Process> processes) {
        int completionTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        Collections.sort(processes, Comparator.comparingInt(p -> p.priority));

        for (Process process : processes) {
            if (process.arrivalTime > completionTime) {
                completionTime = process.arrivalTime;
            }

            int turnaroundTime = completionTime + process.burstTime - process.arrivalTime;
            int waitingTime = turnaroundTime - process.burstTime;

            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;

            completionTime += process.burstTime;

            System.out.println("Process " + process.pid + ": Turnaround Time = " + turnaroundTime + ", Waiting Time = " + waitingTime);
        }

        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / processes.size());
        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / processes.size());
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 0, 5, 3));
        processes.add(new Process(2, 2, 3, 2));
        processes.add(new Process(3, 4, 1, 1));
        processes.add(new Process(4, 6, 4, 4));

        schedulingPriority(processes);
    }
}