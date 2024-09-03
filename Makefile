# Defome the directories
CLIENT_DIR = Client
AGSERVER_DIR = AggregationServer
COSERVER_DIR = ContentServer
SRC_DIR = src
BIN_DIR = bin

# Define the class names
AGSERVER_CLASS = AggregationServer
COSERVER_CLASS = ContentServer
CLIENT_CLASS = GETClient

# Define the Java files
AGSERVER_JAVA = $(SRC_DIR)/$(AGSERVER_DIR)/$(AGSERVER_CLASS).java
COSERVER_JAVA = $(SRC_DIR)/$(COSERVER_DIR)/$(COSERVER_CLASS).java
CLIENT_JAVA = $(SRC_DIR)/$(CLIENT_DIR)/$(CLIENT_CLASS).java

# Default target
all: run

# Target to compile Java files
compile:
	javac -d ./bin $(AGSERVER_JAVA) $(COSERVER_JAVA) $(CLIENT_JAVA) 

# Target to run both server and client
run: compile
	@tmux new-session -d my_session 'make run_server'
	@tmux split-window -h -t my_session 'make run_client'
	@tmux attach -t my_session
	
# Run server
run_server:
	cd $(BIN_DIR) && java $(AGSERVER_DIR)/$(AGSERVER_CLASS)

# Run client
run_client:
	cd $(BIN_DIR) && java $(CLIENT_DIR)/$(CLIENT_CLASS)


# Clean target to remove compiled .class files
clean:
	rm -f $(BIN_DIR)/*.class
