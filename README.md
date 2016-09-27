# DiseaseBasket

This Java program is based on the [Apriori algorithm](http://nikhilvithlani.blogspot.com.ng/2012/03/apriori-algorithm-for-data-mining-made.html). I used it on some hospital data to try get which symptoms usually go together in particular diseases. I got some hospital patient data and made up a couple of tables listing cases of different people with their symptoms. I couldn't add the actual symptoms because I couldn't get permission to share that, but I don't think it's going to be too difficult to read in stuff like "headache", "fever", "stomach ache" etc in place of 'A', 'B', 'C', etc.

The application starts by asking you to write out a disease name. The disease name must correspond to one of the .CSV files available. As many symptoms (files) as required could be added. Then it asks for a support count (a figure between 0.0-1.0) which you can think of as how often a particular combination occurs (and thus, how relevant it is for that level). It then returns an array (or arrays) of symptoms and their respective support counts.

The file looks like this.
![CSV](https://github.com/allengblack/DiseaseBasket/blob/master/Selection_515.png) 
The columns marked A, B, C... are disease symptoms and each "Case" represents an actual patient. The 1's are cases where the specific symptom was present, 0's where it was not.

The output is this:
[Output](https://github.com/allengblack/DiseaseBasket/blob/master/Selection_514.png)

I included three sample diseases. They're the three CSV files. If anything is else is unclear, please contact me.
