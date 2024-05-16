by _Chihab_ :

**Weka**


## 1- PreProcessing (available approches):
### Data Cleaning
  - Missing Values Handling
  - Outlier Detection/Removal
  - Noise Filtering
### Data Transfomation
  - Normalization
  - Standardization
  - Discretization
  - Binning
### Feature ENgineering
  - Feature Scaling
  - Feature Extraction
  - Feature Aggregation
### Data Sampling
  - Stratified sampling
  - Oversampling
  - Undersampling
### Data Splitting (already splitted)
### Data Encoding
  - One-Hot Encoding
  - Label Encoding
  - Oridnal Encoding
### Data Balancing
  - class Balancing
### Data Augmentation
  - Syntehtic Data Geeration

## 2- FeatureSelection
### Attribute Evaluators
- CFS
- InfoGain
- GainRatio
- PCA
- Chi-Squares
- ReliefF
- Wrapper Method
- Symmetrical Uncetainty

  
### Search Methods
- BestFirst
- GreedyStepwise
- Genetic Search
- Ranker

**Possible Combinations between them**


| Attribute Evaluator            | BestFirst | GreedyStepwise | GeneticSearch | Ranker | 
|--------------------------------|-----------|----------------|---------------|--------|
| CfsSubsetEval                  | •         | •              | •             |        |
| InfoGainAttributeEval          | •         | •              |               | •      |
| GainRatioAttributeEval         | •         | •              |               | •      |
| ChiSquaredAttributeEval        | •         | •              |               | •      |
| ReliefFAttributeEval           |           | •              |               | •      |
| WrapperSubsetEval              | •         | •              | •             |        |
| SymmetricalUncertAttributeEval | •         | •              |               | •      |
| PrincipalComponents            | •         | •              |               | •      |
