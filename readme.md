# Multi-Threaded Web Server in Java

## Overview
This project contains three different implementations of a Java-based web server to analyze and compare their performance under high load conditions. The servers serve an HTML file upon receiving an HTTP request.

## Implementations
1. **Single-Threaded Web Server**
   - Handles one client request at a time.
   - Simple implementation but inefficient under high traffic.

2. **Multi-Threaded Web Server**
   - Creates a new thread for each incoming client request.
   - Improves performance but may consume excessive system resources under heavy load.

3. **Multi-Threaded Web Server with Thread Pool**
   - Uses a fixed-size thread pool to manage client requests efficiently.
   - Provides better resource management and stability under high loads.

## Setup Instructions
### **1. Prerequisites**
- Java JDK installed (`>=11` recommended).
- Apache JMeter installed for load testing.

### **2. Running the Server**
Each server implementation has a `Server.java` file. To start a server:
```sh
javac Server.java
java Server
```
By default, the server listens on `port 8010`.

### **3. Running the Client**
A simple client program is provided to send HTTP requests to the server:
```sh
javac Client.java
java Client
```
This client connects to `localhost:8010` and sends an HTTP `GET` request.

## Load Testing with JMeter
To test each server’s performance, a JMeter test plan (`LoadTest.jmx`) is included.

### **1. Running the Load Test**
1. Open Apache JMeter.
2. Load `LoadTest.jmx`.
3. Start the test and observe results.

### **2. Key Metrics for Comparison**
- **Throughput**: Number of requests processed per second.
- **Response Time**: Time taken to serve each request.
- **Error Rate**: Percentage of failed requests.

## Expected Observations
- The **Single-Threaded Server** performs worst under load.
- The **Multi-Threaded Server** improves performance but may cause excessive resource consumption.
- The **Thread-Pooled Server** balances performance and resource utilization effectively.

## Testing Results:
Test Results are included in the /test directory.

### Result Conclusions: 
While both multi-threaded approaches vastly outperform the single-threaded model, the Thread Pool server demonstrated superior performance characteristics in terms of consistency and lower latency across various metrics (Average, Median, Percentiles), despite a slightly lower peak throughput in this specific test. The high error rates suggest initial stress, but the Thread Pool manages the load more predictably once stabilized. Although the Response Time Graphs didn't visually distinguish the two concurrent approaches due to dominant warm-up effects and the simple workload, the Aggregate data confirms the benefits of thread pooling. The Thread Pool architecture is the recommended approach for building robust and scalable concurrent servers.

## Contributing
Feel free to fork this repository and experiment with different server architectures!

