# Define the Java files
CLIENT_DIR = src/Client
AGSERVER_DIR = src/AggregationServer
COSERVER_DIR = src/ContentServer
AGSERVER_JAVA = $(AGSERVER_DIR)/AggregationServer.java
COSERVER_JAVA = $(COSERVER_DIR)/ContentServer.java
CLIENT_JAVA = $(CLIENT_DIR)/Client.java

# Define the class names
AGSERVER_CLASS = AggregationServer
COSERVER_CLASS = ContentServer
CLIENT_CLASS = Client

# Define the terminal command
TERMINAL_CMD = gnome-terminal -- bash -c

# Default target
all: run

# Target to compile Java files
compile:
	javac -d ./bin $(AGSERVER_JAVA) $(COSERVER_JAVA) $(CLIENT_JAVA) 

# Target to run both server and client
run: compile
	$(TERMINAL_CMD) "java $(AGSERVER_CLASS); exec bash" \
	--tab --title "Server" \
	$(TERMINAL_CMD) "java $(CLIENT_CLASS); exec bash" \
	--tab --title "Client"

# Clean target to remove compiled .class files
clean:
	rm -f *.class
