@echo This test need JDK8+ 
call mvn clean compile exec:exec -Psplit_startup -Dstartup.iteration=100 -Dstartup.warmup=10
@pause