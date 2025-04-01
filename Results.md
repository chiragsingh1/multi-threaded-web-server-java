**1. Objective:**
Evaluate the performance of Single-Threaded, Multi-Threaded (Thread-per-Request), and Multi-Threaded (Thread Pool) Java web servers under concurrent load using JMeter.

**2. Methodology Summary:**
Three server types serving a static `index.html` with `Connection: close` were tested locally. JMeter simulated 100 concurrent users ramping up over 5 seconds for a 60-second duration, with a 3000ms response timeout.

**3. Results & Analysis:**

*   **Single-Threaded Server:**
    *   Completely failed under load (**99.61% Error Rate**) due to sequential processing limitations causing mass connection refusals. Rendered unusable for concurrency. Performance metrics for the few successful requests (~0.4%) are statistically insignificant.

*   **Multi-Threaded Server (Thread-per-Request):**
    *   Handled concurrency but showed significant stress (**37.90% Error Rate**, likely timeouts).
    *   Achieved the highest successful throughput (~40.8/sec).
    *   Exhibited performance inconsistency: high Average latency (96ms) compared to Median (4ms), and poor tail latency (99% Line: 2184ms).

*   **Multi-Threaded Server (Thread Pool):**
    *   Handled concurrency with marginally fewer errors (**36.10% Error Rate**) than the thread-per-request model.
    *   Throughput was slightly lower (~36.1/sec).
    *   Demonstrated **superior consistency and lower latency**: significantly better Average (70ms) and Median (2ms) times, and notably improved tail latency (99% Line: 1737ms).
    *   *Note:* Response Time Graphs appeared visually similar for both concurrent servers, likely dominated by JVM warm-up, OS caching, and `Connection: close` overhead, masking underlying efficiency differences in the average trend visualization. The Aggregate Report data reveals the actual performance distinctions.

**4. Comparison Highlights:**

| Metric         | Single-Threaded | Multi-Threaded (Thread-per-Request) | **Multi-Threaded (Thread Pool)** | Notes                             |
| :------------- | :-------------- | :---------------------------------- | :----------------------------- | :-------------------------------- |
| **Error %**    | 99.61% (Fail)   | 37.90%                              | **36.10%**                     | Pool slightly better              |
| **Throughput** | ~0/sec (actual) | **~40.8/sec**                       | ~36.1/sec                      | Higher doesn't mean better here |
| **Average**    | (N/A)           | 96ms                                | **70ms**                       | Pool significantly more consistent |
| **Median**     | (N/A)           | 4ms                                 | **2ms**                        | Pool faster typically             |
| **99% Line**   | (N/A)           | 2184ms                              | **1737ms**                     | Pool much better worst-case       |

**5. Conclusion:**

The Single-Threaded server is inadequate for concurrent loads. While both multi-threaded approaches handled concurrency far better, they experienced high initial error rates under the test conditions.

Critically, the **Thread Pool server demonstrated clear advantages in performance quality**. Despite slightly lower peak throughput in this specific test, it provided **significantly lower average latency, faster typical response times (Median), and substantially reduced tail latency (99% Line)**. This indicates greater efficiency and predictability by reusing threads, avoiding the overhead and contention associated with creating a new thread for every request.

Based on the superior consistency and latency metrics, the **Thread Pool architecture is the most robust and performant approach** among the three tested for handling concurrent web requests.