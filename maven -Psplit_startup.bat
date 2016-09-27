@echo This test need JDK8+
call mvn clean compile exec:exec -Psplit_startup
@pause