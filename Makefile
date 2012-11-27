#Makefile for wfdb-java-applications

SOURCE_DIR := src
OUTPUT_DIR := build

#Tools used by packaging
FIND := /usr/bin/find

#Java Tools
JAVA := /usr/bin/java
BUILDFILE_DIR := /home/joe/workspace/wfdb-java\(TRUNK\)/build.xml
JARFLAGS := -jar		\
		  /lib64/eclipse/plugins/org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar				\
		  -application org.eclipse.ant.core.antRunner	\
		  -buildfile $(BUILDFILE_DIR)

#all_javas - Temp file for holding source file list
all_javas := $(OUTPUT_DIR)/all.javas

#compile the source
.PHONY: jar
jar: 
	$(JAVA) $(JARFLAGS) @$<
	
#Gather rouisce file list
#.INTERMEDIATE: $(all_javas)
#$(all_javas):
#$(FIND) $(SOURCE_DIR) -name '*.java' > $@
#	cat $(OUTPUT_DIR)/all.javas