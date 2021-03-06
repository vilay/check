#
# (C) Copyright IBM Corp. 2001
#
#
# $Id: Makefile,v 1.4 2003/08/13 20:08:52 dgrove-oss Exp $
#
# @author Julian Dolby
# @author Stephen Fink

include         $(RVM_BUILD)/Make.rules.target

BENCH_NAME=Xalan
include		$(JAL_ROOT)/rvm/regression/Local.rules

HEAPSIZE=250

DRIVER_SOURCE_PATH=$(JAL_ROOT)/rvm/src/examples/iterationDriver
BENCH_SOURCE_PATH=$(DRIVER_SOURCE_PATH)
BENCH_CLASS_PATH=$(BENCH_HOME)/bin/xalan.jar:$(BENCH_HOME)/bin/xercesImpl.jar:$(BENCH_HOME)/bin/xml-apis.jar:$(BENCH_HOME)/bin/xalansamples.jar:$(BENCH_HOME)/bin/xsltc.jar

ifeq ($(MODE),PERFORMANCE)
  ITERATIONS=11
else
  ITERATIONS=1
endif

START_NAME=Driver
START_ARGS=$(ITERATIONS) SimpleTransform main $(INPUT_FILES)
BENCH_RVM_ARGS=-Djavax.xml.transform.TransformerFactory=org.apache.xalan.xsltc.trax.TransformerFactoryImpl -Djavax.xml.parsers.SAXParserFactory=org.apache.xerces.jaxp.SAXParserFactoryImpl

default:	sanity

$(WORKING)/birds.xsl:		$(WORKING)
ifneq ($(WORKING),.)
	cp -f $(BENCH_HOME)/samples/SimpleTransform/birds.xsl $(WORKING)
endif

$(WORKING)/birds.xml:		$(WORKING)
ifneq ($(WORKING),.)
	cp -f $(BENCH_HOME)/samples/SimpleTransform/birds.xml $(WORKING)
endif


sanity: $(WORKING)/birds.xml $(WORKING)/birds.xsl
	$(MAKE) sanity-check-rule

include		$(JAL_ROOT)/rvm/regression/Make.rules

do-gather-performance:
	 @$(AWK) -f $(JAL_ROOT)/rvm/regression/tests/xerces/perf.awk $(OUT) >> $(PERF_LOG)

check:  
	@$(FGREP) -q "The result is in birds.out" < $(OUT)
