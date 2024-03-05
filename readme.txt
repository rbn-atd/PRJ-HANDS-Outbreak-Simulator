HANDS

* Dependencies

- Git >= 2.39
- JDK (Java SE Development Kit) <= 1.8
- Apache Maven >= 3.8i 
- Gambit >= 16.0
- gnuplot

* Configuration

1. Run `git submodule init`
2. Run `git submodule update`
3. Create folder `output/data`
4. Create folder `output/charts/tables` and `output/charts/figures`
5. Create `output/simulationSchedule.txt` (template provided)

* Build and Run

1. Build with `mvn clean package`
2. Execute Runner within resulting JAR, e.g. `java -cp target/hands-1.0-SNAPSHOT.jar org.kclhi.hands.utility.Runner`
