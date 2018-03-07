JFLAGS = -g
JC = javac
NAME = "Doodle"
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLASGS) $*.java

CLASSES = \
	Doodle.java \
	Canvas.java \
	Menu.java \
	Model.java \
	Palette.java \
	Playback.java \
	Segment.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	@echo "Running"
	java $(NAME)

clean:
	$(RM) *.class

