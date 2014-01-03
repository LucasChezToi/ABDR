LC     = pdflatex
LFLAGS = -file-line-error \
	 -halt-on-error	  \
	 -output-directory $(OBJ) 

SRC = src
OBJ = tmp
BIN = bin

all: dirs target 


DIRS = $(OBJ) $(BIN)
dirs: | $(DIRS)
$(DIRS):
	mkdir -p $(DIRS)

TRGT = $(BIN)/Rapport.pdf
target: 
	$(LC) $(LFLAGS)	$(SRC)/Rapport.tex
	mv $(OBJ)/Rapport.pdf $(TRGT)
	@touch $(TRGT) # To refresh the PDF viewer
	

clean:
	rm -f $(TRGT)
	rm -f $(SRC)/*~
	rm -f $(OBJ)/*

evince :
	evince $(BIN)/Rapport.pdf