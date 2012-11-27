#Makefile for wfdb-java-applications

SOURCE_DIR := src
OUTPUT_DIR := build

#Tools used by packaging
FIND := /usr/bin/find

#Java Tools
JAVA := /usr/bin/java
JAVAC := /usr/bin/javac
JFLAGS := -sourcepath $(SOURCE_DIR)		\
		  -d $(OUTPUT_DIR)				\
		  -source 1.4

#all_javas - Temp file for holding source file list
all_javas := $(OUTPUT_DIR)/all.javas

#compile the source
.PHONY: compile
compile: $(all_javas)
	$(JAVAC) $(JFLAGS) @$<
	
#Gather rouisce file list
.INTERMEDIATE: $(all_javas)
$(all_javas):
	$(FIND) $(SOURCE_DIR) -name '*.java' > $@
	cat $(OUTPUT_DIR)/all.javas