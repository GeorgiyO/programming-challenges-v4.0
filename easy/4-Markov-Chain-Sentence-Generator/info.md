#4 Markov Chain Sentence Generator:

###Description:

Text Generator (Markov Chain) Tool to generate text from Markof's chains.
Markov Chains allow the prediction of a future state based on the 
characteristics of a present state. Suitable for text, the principle of
Markov chain can be turned into a sentences generator.

###Theory:

* https://habr.com/ru/post/455762/ (russian)

###Technologies:

* Language: Java v15
* External Libs: None
* UI: Web interface using HTML + CSS + Vanilla JS

###Solution explanation:

1. Get variables:
    * Initial text
    * Number of sentences
1. Parse it to words (including punctuation marks)
1. Build Markov Chain with calculating the probability of occurrence of a word in
    * Beginning of sentence
    * End of sentence
    * After another word
1. Build final text relying on the Markov Chain and Number of sentences

