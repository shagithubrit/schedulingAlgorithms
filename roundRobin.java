import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int remainingTime;

    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

class RoundRobin {
    public static void schedulingRoundRobin(List<Process> processes, int timeSlice) {
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        Queue<Process> readyQueue = new LinkedList<>();
        List<Process> completedProcesses = new ArrayList<>();

        // Add all processes with arrival time 0 to the ready queue initially
        for (Process process : processes) {
            if (process.arrivalTime == 0) {
                readyQueue.offer(process);
            }
        }

        while (!readyQueue.isEmpty() || !completedProcesses.isEmpty()) {
            // If there are processes in the ready queue
            if (!readyQueue.isEmpty()) {
                Process process = readyQueue.poll();
                int executionTime = Math.min(timeSlice, process.remainingTime);
                process.remainingTime -= executionTime;
                currentTime += executionTime;

                // Add the process back to the ready queue if it hasn't completed
                if (process.remainingTime > 0) {
                    readyQueue.offer(process);
                } else {
                    // Calculate turnaround time and waiting time for the completed process
                    int turnaroundTime = currentTime - process.arrivalTime;
                    int waitingTime = turnaroundTime - process.burstTime;
                    totalTurnaroundTime += turnaroundTime;
                    totalWaitingTime += waitingTime;
                    completedProcesses.add(process);
                    System.out.println("Process " + process.pid + ": Turnaround Time = " + turnaroundTime + ", Waiting Time = " + waitingTime);
                }
            }

            // Add any new processes that have arrived to the ready queue
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && !readyQueue.contains(process) && !completedProcesses.contains(process)) {
                    readyQueue.offer(process);
                }
            }
        }

        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / processes.size());
        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / processes.size());
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 0, 8));
        processes.add(new Process(2, 1, 4));
        processes.add(new Process(3, 2, 9));
        processes.add(new Process(4, 3, 5));

        int timeSlice = 2; // Time slice or time quantum
        schedulingRoundRobin(processes, timeSlice);
    }
}