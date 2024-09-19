# Defome the directories
CLIENT_DIR = Client
AGSERVER_DIR = AggregationServer
TEST_DIR = Test
LAMPORT_DIR = Lamport
SRC_DIR = src
BIN_DIR = bin
LIB_DIR = lib

# Dependencies, etc
CLASSPATH = -cp ./bin:./lib/junit-platform-console-standalone-1.11.0.jar:./lib/json-simple-1.1.1.jar
DUMP = -d ./bin
FLAGS = $(CLASSPATH) $(DUMP)

# Define the class names
AGSERVER_CLASS = AggregationServer
COSERVER_CLASS = ContentServer
GETCLIENT_CLASS = GETClient
CLIENT_CLASS = Client
# CLIENT_TEST = ClientTest
# LAMPORT_CLOCK_TEST = LamportClockImplTest
# NETWORK_TEST = NetworkTest
# NODE_TEST = NodeTest
# PROCESSOR_TEST = ProcessorTest

# Define the Java files
AGSERVER_JAVA = $(SRC_DIR)/$(AGSERVER_DIR)/$(AGSERVER_CLASS).java
CLIENT_JAVA = $(SRC_DIR)/$(CLIENT_DIR)/*.java
TEST_JAVA = $(SRC_DIR)/$(TEST_DIR)/*.java
LAMPORT_JAVA = $(SRC_DIR)/$(LAMPORT_DIR)/*.java

# Args
PORT = 4567
URI = http://127.0.0.1:$(PORT)
CONTENTPATH = /home/zachie2212/DS-Assignment-2/$(SRC_DIR)/$(CLIENT_DIR)/TestData.txt
ID = 

# Default target
all: compile

# Target to compile Java files
compile:
	javac $(FLAGS) $(AGSERVER_JAVA) $(CLIENT_JAVA) $(COSERVER_JAVA) $(GETCLIENT_JAVA) $(LAMPORT_JAVA) $(TEST_JAVA)
	
# Run aggregation server
server:
	java $(CLASSPATH) $(AGSERVER_DIR).$(AGSERVER_CLASS) $(PORT)

# Run client
client:
	java $(CLASSPATH) $(CLIENT_DIR).$(GETCLIENT_CLASS) $(URI) $(ID)

# Run content server
content:
	java $(CLASSPATH) $(CLIENT_DIR).$(COSERVER_CLASS) $(URI) $(CONTENTPATH)

# Clean target to remove compiled .class files
clean:
	rm -f $(BIN_DIR)/*.class
