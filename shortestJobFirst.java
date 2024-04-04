import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;

    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

class shortestJobFirst {
    public static void schedulingSJF(List<Process> processes) {
        int completionTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        Collections.sort(processes, Comparator.comparingInt(p -> p.burstTime));

        for (Process process : processes) {
            if (process.arrivalTime > completionTime) {
                completionTime += process.arrivalTime;
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
        processes.add(new Process(1, 0, 5));
        processes.add(new Process(2, 2, 3));
        processes.add(new Process(3, 4, 1));
        processes.add(new Process(4, 6, 4));

        schedulingSJF(processes);
    }
}