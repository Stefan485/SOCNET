# SOCNET
SR:
Menjanje vrednosti metrika za socijalne mreže kroz k-jezgra

Implementacija Batagelj-Zaverštnik algoritma za dekompoziciju mreže na K-jezgra, Erdos-Renyi, Barabasi-Albert i Jezgro-Periferija modela.
Računanje metrika centralnosti (ugnježdenost, centralnost i eignevector) za kompletnu mrežu.
Računanje globalnih metrika za celu mrežu i svako k-jezgro: Koeficijent klasterisanja, koeficijent malog sveta, indeks asortativnosti...

JDK verzija: 19
VM parametar: -Xmx32768m (dodeljivanje RAM memorije po potrebi)
Biblioteke: JUNG, Apache Commons math 3.6.1

ENG:
Changing of values for social network metrics trough k-cores

JDK vesrsion: 19\
VM parameter: -Xmx32768m (after implementing centrality metric insted of using the one from the library needed about 4Gb of ram)
Libraries:JUNG, Apache Commons math 3.6.1

Implementation of Batagelj-Zaverstnik algorithm for decomposition of network on K-cores, Erdos-Renyi, Barababsi-Albert, and Core-Periphery model.
Calculation of centrality metrics (betweenness, closeness, eigenvector) for the complete network.
Calculation of global metrics for the entire network and each k-core: clustering coeficient, small-world coeficient, assortativity index...
