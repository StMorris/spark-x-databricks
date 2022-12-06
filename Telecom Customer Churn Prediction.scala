// Databricks notebook source
// DBTITLE 1,Customer Attrition
// MAGIC %md
// MAGIC 
// MAGIC **Customer attrition**, also known as customer churn, customer turnover, or customer defection, is the loss of clients or customers.
// MAGIC 
// MAGIC Telephone service companies, Internet service providers, pay TV companies, insurance firms, and alarm monitoring services, often use customer attrition analysis and customer attrition rates as one of their key business metrics because the cost of retaining an existing customer is far less than acquiring a new one. Companies from these sectors often have customer service branches which attempt to win back defecting clients, because recovered long-term customers can be worth much more to a company than newly recruited clients.
// MAGIC 
// MAGIC Companies usually make a distinction between voluntary churn and involuntary churn. Voluntary churn occurs due to a decision by the customer to switch to another company or service provider, involuntary churn occurs due to circumstances such as a customer's relocation to a long-term care facility, death, or the relocation to a distant location. In most applications, involuntary reasons for churn are excluded from the analytical models. Analysts tend to concentrate on voluntary churn, because it typically occurs due to factors of the company-customer relationship which companies control, such as how billing interactions are handled or how after-sales help is provided.
// MAGIC 
// MAGIC **Predictive analytics use churn prediction models** that predict customer churn by assessing their propensity of risk to churn. Since these models generate a small prioritized list of potential defectors, they are effective at focusing customer retention marketing programs on the subset of the customer base who are most vulnerable to churn.

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC **Context**
// MAGIC 
// MAGIC Predict behavior to retain customers. You can analyze all relevant customer data and develop focused customer retention programs.
// MAGIC 
// MAGIC **Content**
// MAGIC 
// MAGIC Each row represents a customer, each column contains customer’s attributes described on the column Metadata.
// MAGIC 
// MAGIC **The data set includes information about:**
// MAGIC 
// MAGIC * Customers who left within the last month – the column is called Churn
// MAGIC * Services that each customer has signed up for – phone, multiple lines, internet, online security, online backup, device protection, tech support, and streaming TV and movies
// MAGIC * Customer account information – how long they’ve been a customer, contract, payment method, paperless billing, monthly charges, and total charges
// MAGIC * Demographic info about customers – gender, age range, and if they have partners and dependents
// MAGIC 
// MAGIC **Inspiration**
// MAGIC 
// MAGIC To explore this type of models and learn more about the subject.

// COMMAND ----------

// MAGIC %scala
// MAGIC 
// MAGIC import org.apache.spark.sql.Encoders;
// MAGIC 
// MAGIC case class telecom(customerID: String, 
// MAGIC                    gender: String, 
// MAGIC                    SeniorCitizen: Int, 
// MAGIC                    Partner: String, 
// MAGIC                    Dependents: String, 
// MAGIC                    tenure: Int, 
// MAGIC                    PhoneService: String, 
// MAGIC                    MultipleLines: String, 
// MAGIC                    InternetService: String, 
// MAGIC                    OnlineSecurity: String, 
// MAGIC                    OnlineBackup: String, 
// MAGIC                    DeviceProtection: String, 
// MAGIC                    TechSupport: String, 
// MAGIC                    StreamingTV: String, 
// MAGIC                    StreamingMovies: String, 
// MAGIC                    Contract: String, 
// MAGIC                    PaperlessBilling: String, 
// MAGIC                    PaymentMethod: String, 
// MAGIC                    MonthlyCharges: Double, 
// MAGIC                    TotalCharges: Double, 
// MAGIC                    Churn: String )
// MAGIC 
// MAGIC val telecomSchema = Encoders.product[telecom].schema
// MAGIC 
// MAGIC val telecomDF = spark.read.schema(telecomSchema).option("header", "true").csv("/FileStore/tables/TelcoCustomerChurn.csv")
// MAGIC 
// MAGIC display(telecomDF)

// COMMAND ----------

// DBTITLE 1,Printing Schema
telecomDF.printSchema()

// COMMAND ----------

// DBTITLE 1,Creating Temp View from Dataframe 
// MAGIC %scala
// MAGIC 
// MAGIC telecomDF.createOrReplaceTempView("TelecomData")

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC #Exploratory Data Analysis
// MAGIC 
// MAGIC * What is EDA?
// MAGIC * Exploratory Data Analysis (EDA) is an approach/philosophy for data analysis that employs a variety of techniques (mostly graphical) to
// MAGIC  1) maximize insight into a data set;
// MAGIC  2) uncover underlying structure:
// MAGIC  3) extract important variables;
// MAGIC  4) detect outliers and anomalies;
// MAGIC  5) test underlying assumptions;
// MAGIC  6) develop parsimo-tous models; and
// MAGIC  7) determine optima factor settings.

// COMMAND ----------

// DBTITLE 1,Customer Attrition in Data
// MAGIC %sql
// MAGIC 
// MAGIC select Churn, count(Churn) 
// MAGIC from TelecomData 
// MAGIC group by Churn;

// COMMAND ----------

// DBTITLE 1,Gender Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select gender,count(gender), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,gender;

// COMMAND ----------

// DBTITLE 1,SeniorCitizen Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select SeniorCitizen,count(SeniorCitizen), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,SeniorCitizen;

// COMMAND ----------

// DBTITLE 1,Partner Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select Partner,count(Partner), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,Partner;

// COMMAND ----------

// DBTITLE 1,Dependents Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select Dependents,count(Dependents), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,Dependents;

// COMMAND ----------

// DBTITLE 1,PhoneService Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select PhoneService,count(PhoneService), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,PhoneService;

// COMMAND ----------

// DBTITLE 1,MultipleLines Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select MultipleLines,count(MultipleLines), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,MultipleLines;

// COMMAND ----------

// DBTITLE 1,InternetService Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select InternetService,count(InternetService), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,InternetService;

// COMMAND ----------

// DBTITLE 1,OnlineSecurity Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select OnlineSecurity,count(OnlineSecurity), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,OnlineSecurity;

// COMMAND ----------

// DBTITLE 1,OnlineBackup Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select OnlineBackup,count(OnlineBackup), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,OnlineBackup;

// COMMAND ----------

// DBTITLE 1,DeviceProtection Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select DeviceProtection,count(DeviceProtection), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,DeviceProtection;

// COMMAND ----------

// DBTITLE 1,TechSupport Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select TechSupport,count(TechSupport), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,TechSupport;

// COMMAND ----------

// DBTITLE 1,StreamingTV Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select StreamingTV,count(StreamingTV), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,StreamingTV;

// COMMAND ----------

// DBTITLE 1,StreamingMovies Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select StreamingMovies,count(StreamingMovies), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,StreamingMovies;

// COMMAND ----------

// DBTITLE 1,Contract Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select Contract,count(Contract), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,Contract;

// COMMAND ----------

// DBTITLE 1,PaperlessBilling Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select PaperlessBilling,count(PaperlessBilling), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,PaperlessBilling;

// COMMAND ----------

// DBTITLE 1,PaymentMethod Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select PaymentMethod,count(PaymentMethod), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,PaymentMethod;

// COMMAND ----------

// DBTITLE 1,Tenure Group Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select cast ((TotalCharges/MonthlyCharges)/12 as Int) as Tenure, count(cast ((TotalCharges/MonthlyCharges)/12 as Int)), Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn, cast ((TotalCharges/MonthlyCharges)/12 as Int);

// COMMAND ----------

// DBTITLE 1,Tenure Group Distribution in Customer Attrition
// MAGIC %sql
// MAGIC 
// MAGIC select cast ((TotalCharges/MonthlyCharges)/12 as Int) as Tenure, count(cast ((TotalCharges/MonthlyCharges)/12 as Int)) as counts, Churn  
// MAGIC from TelecomData 
// MAGIC group by Churn,cast ((TotalCharges/MonthlyCharges)/12 as Int)  
// MAGIC order by Tenure;

// COMMAND ----------

// DBTITLE 1,Monthly Charges & Total Charges by Tenure group
// MAGIC %sql
// MAGIC 
// MAGIC select TotalCharges, MonthlyCharges, cast ((TotalCharges/MonthlyCharges)/12 as Int) as Tenure 
// MAGIC from TelecomData;

// COMMAND ----------

// MAGIC %md ## Creating a Classification Model
// MAGIC 
// MAGIC In this Project, you will implement a classification model **(Logistic Regression)** that uses features of telecom details of customer and we will predict it is Churn or Not
// MAGIC 
// MAGIC ### Import Spark SQL and Spark ML Libraries
// MAGIC 
// MAGIC First, import the libraries you will need:

// COMMAND ----------

import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.VectorAssembler

// COMMAND ----------

// MAGIC %md ### Prepare the Training Data
// MAGIC To train the classification model, you need a training data set that includes a vector of numeric features, and a label column. In this project, you will use the **VectorAssembler** class to transform the feature columns into a vector, and then rename the **Churn** column to **label**.

// COMMAND ----------

// MAGIC %md ###VectorAssembler()
// MAGIC 
// MAGIC VectorAssembler():  is a transformer that combines a given list of columns into a single vector column. It is useful for combining raw features and features generated by different feature transformers into a single feature vector, in order to train ML models like logistic regression and decision trees. 
// MAGIC 
// MAGIC **VectorAssembler** accepts the following input column types: **all numeric types, boolean type, and vector type.** 
// MAGIC 
// MAGIC In each row, the **values of the input columns will be concatenated into a vector** in the specified order.

// COMMAND ----------

// MAGIC %scala
// MAGIC 
// MAGIC var StringfeatureCol = Array("customerID", "gender", "Partner", "Dependents", "PhoneService", "MultipleLines", "InternetService", "OnlineSecurity", "OnlineBackup", "DeviceProtection", "TechSupport", "StreamingTV", "StreamingMovies", "Contract", "PaperlessBilling", "PaymentMethod", "Churn")

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC ###StringIndexer
// MAGIC 
// MAGIC StringIndexer encodes a string column of labels to a column of label indices.

// COMMAND ----------

// DBTITLE 1,Example of StringIndexer
import org.apache.spark.ml.feature.StringIndexer

val df = spark.createDataFrame(
  Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c"))
).toDF("id", "category")

val indexer = new StringIndexer()
  .setInputCol("category")
  .setOutputCol("categoryIndex")

val indexed = indexer.fit(df).transform(df)

display(indexed)

// COMMAND ----------

// MAGIC %md ### Define the Pipeline
// MAGIC A predictive model often requires multiple stages of feature preparation. 
// MAGIC 
// MAGIC A pipeline consists of a series of *transformer* and *estimator* stages that typically prepare a DataFrame for modeling and then train a predictive model. 
// MAGIC 
// MAGIC In this case, you will create a pipeline with stages:
// MAGIC 
// MAGIC - A **StringIndexer** estimator that converts string values to indexes for categorical features
// MAGIC - A **VectorAssembler** that combines categorical features into a single vector

// COMMAND ----------

// MAGIC %scala
// MAGIC 
// MAGIC import org.apache.spark.ml.attribute.Attribute
// MAGIC import org.apache.spark.ml.feature.{IndexToString, StringIndexer}
// MAGIC import org.apache.spark.ml.{Pipeline, PipelineModel}
// MAGIC 
// MAGIC val indexers = StringfeatureCol.map { colName =>
// MAGIC   new StringIndexer().setInputCol(colName).setHandleInvalid("skip").setOutputCol(colName + "_indexed")
// MAGIC }
// MAGIC 
// MAGIC val pipeline = new Pipeline()
// MAGIC                     .setStages(indexers)      
// MAGIC 
// MAGIC val TelDF = pipeline.fit(telecomDF).transform(telecomDF)

// COMMAND ----------

// DBTITLE 1,Printing Schema
TelDF.printSchema()

// COMMAND ----------

// DBTITLE 1,Data Display
TelDF.show()

// COMMAND ----------

// DBTITLE 1,Count of Records
// MAGIC %scala
// MAGIC 
// MAGIC TelDF.count()

// COMMAND ----------

// MAGIC %md ### Split the Data
// MAGIC It is common practice when building supervised machine learning models to split the source data, using some of it to train the model and reserving some to test the trained model. In this project, you will use 70% of the data for training, and reserve 30% for testing. 

// COMMAND ----------

// MAGIC %scala
// MAGIC 
// MAGIC val splits = TelDF.randomSplit(Array(0.7, 0.3))
// MAGIC val train = splits(0)
// MAGIC val test = splits(1)
// MAGIC val train_rows = train.count()
// MAGIC val test_rows = test.count()
// MAGIC println("Training Rows: " + train_rows + " Testing Rows: " + test_rows)

// COMMAND ----------

// MAGIC %scala
// MAGIC 
// MAGIC import org.apache.spark.ml.feature.VectorAssembler
// MAGIC 
// MAGIC val assembler = new VectorAssembler().setInputCols(Array("customerID_indexed", "gender_indexed", "SeniorCitizen", "Partner_indexed", "Dependents_indexed", "PhoneService_indexed", "MultipleLines_indexed", "InternetService_indexed", "OnlineSecurity_indexed", "OnlineBackup_indexed", "DeviceProtection_indexed", "TechSupport_indexed", "StreamingTV_indexed", "StreamingMovies_indexed", "Contract_indexed", "PaperlessBilling_indexed", "PaymentMethod_indexed", "tenure", "MonthlyCharges", "TotalCharges" )).setOutputCol("features")
// MAGIC val training = assembler.transform(train).select($"features", $"Churn_indexed".alias("label"))
// MAGIC training.show()

// COMMAND ----------

// MAGIC %md ### Train a Classification Model (Logistic Regression)
// MAGIC Next, you need to train a Classification Model using the training data. To do this, create an instance of the Logistic regression algorithm you want to use and use its **fit** method to train a model based on the training DataFrame. In this Project, you will use a *Logistic Regression* algorithm 

// COMMAND ----------

// MAGIC %scala
// MAGIC 
// MAGIC import org.apache.spark.ml.classification.LogisticRegression
// MAGIC 
// MAGIC val lr = new LogisticRegression().setLabelCol("label").setFeaturesCol("features").setMaxIter(10).setRegParam(0.3)
// MAGIC val model = lr.fit(training)
// MAGIC println("Model Trained!")

// COMMAND ----------

// MAGIC %md ### Prepare the Testing Data
// MAGIC Now that you have a trained model, you can test it using the testing data you reserved previously. First, you need to prepare the testing data in the same way as you did the training data by transforming the feature columns into a vector. This time you'll rename the **Churn_indexed** column to **trueLabel**.

// COMMAND ----------

// MAGIC %scala
// MAGIC 
// MAGIC val testing = assembler.transform(test).select($"features", $"Churn_indexed".alias("trueLabel"))
// MAGIC testing.show()

// COMMAND ----------

// MAGIC %md ### Test the Model
// MAGIC Now you're ready to use the **transform** method of the model to generate some predictions. But in this case you are using the test data which includes a known true label value, so you can compare the predicted Churn. 

// COMMAND ----------

// MAGIC %scala
// MAGIC 
// MAGIC val prediction = model.transform(testing)
// MAGIC val predicted = prediction.select("features", "prediction", "trueLabel")
// MAGIC predicted.show(200)

// COMMAND ----------

// MAGIC %md Looking at the result, the **prediction** column contains the predicted value for the label, and the **trueLabel** column contains the actual known value from the testing data. It looks like there is some variance between the predictions and the actual values (the individual differences are referred to as *residuals*) you'll learn how to measure the accuracy of a model.

// COMMAND ----------

// MAGIC %md ### Compute Confusion Matrix Metrics
// MAGIC Classifiers are typically evaluated by creating a *confusion matrix*, which indicates the number of:
// MAGIC - True Positives
// MAGIC - True Negatives
// MAGIC - False Positives
// MAGIC - False Negatives
// MAGIC 
// MAGIC From these core measures, other evaluation metrics such as *precision* and *recall* can be calculated.

// COMMAND ----------

val tp = predicted.filter("prediction == 1 AND truelabel == 1").count().toFloat
val fp = predicted.filter("prediction == 1 AND truelabel == 0").count().toFloat
val tn = predicted.filter("prediction == 0 AND truelabel == 0").count().toFloat
val fn = predicted.filter("prediction == 0 AND truelabel == 1").count().toFloat
val metrics = spark.createDataFrame(Seq(
 ("TP", tp),
 ("FP", fp),
 ("TN", tn),
 ("FN", fn),
 ("Precision", tp / (tp + fp)),
 ("Recall", tp / (tp + fn)))).toDF("metric", "value")
metrics.show()

// COMMAND ----------

// MAGIC %md ### View the Raw Prediction and Probability
// MAGIC The prediction is based on a raw prediction score that describes a labelled point in a logistic function. This raw prediction is then converted to a predicted label of 0 or 1 based on a probability vector that indicates the confidence for each possible label value (in this case, 0 and 1). The value with the highest confidence is selected as the prediction.

// COMMAND ----------

prediction.select("rawPrediction", "probability", "prediction", "trueLabel").show(100, truncate=false)

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC Note that the results include rows where the probability for 0 (the first value in the probability vector) is only slightly higher than the probability for 1 (the second value in the probability vector). The default discrimination threshold (the boundary that decides whether a probability is predicted as a 1 or a 0) is set to 0.5; so the prediction with the highest probability is always used, no matter how close to the threshold.

// COMMAND ----------

// MAGIC %md ### Review the Area Under ROC
// MAGIC Another way to assess the performance of a classification model is to measure the area under a ROC curve for the model. the spark.ml library includes a **BinaryClassificationEvaluator** class that you can use to compute this. The ROC curve shows the True Positive and False Positive rates plotted for varying thresholds.

// COMMAND ----------

import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator

val evaluator = new BinaryClassificationEvaluator().setLabelCol("trueLabel").setRawPredictionCol("rawPrediction").setMetricName("areaUnderROC")
val auc = evaluator.evaluate(prediction)
println("AUC = " + (auc))

// COMMAND ----------

// MAGIC %md ### Train a Naive Bayes Model
// MAGIC Naive Bayes can be trained very efficiently. With a single pass over the training data, it computes the conditional probability distribution of each feature given each label. For prediction, it applies Bayes’ theorem to compute the conditional probability distribution of each label given an observation.

// COMMAND ----------

// MAGIC %scala
// MAGIC 
// MAGIC import org.apache.spark.ml.classification.NaiveBayes
// MAGIC import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
// MAGIC 
// MAGIC import org.apache.spark.ml.feature.VectorAssembler
// MAGIC 
// MAGIC val assembler = new VectorAssembler().setInputCols(Array("customerID_indexed", "gender_indexed", "SeniorCitizen", "Partner_indexed", "Dependents_indexed", "PhoneService_indexed", "MultipleLines_indexed", "InternetService_indexed", "OnlineSecurity_indexed", "OnlineBackup_indexed", "DeviceProtection_indexed", "TechSupport_indexed", "StreamingTV_indexed", "StreamingMovies_indexed", "Contract_indexed", "PaperlessBilling_indexed", "PaymentMethod_indexed", "tenure", "MonthlyCharges", "TotalCharges" )).setOutputCol("features")
// MAGIC 
// MAGIC val training = assembler.transform(TelDF).select($"features", $"Churn_indexed".alias("label"))
// MAGIC 
// MAGIC // Split the data into training and test sets (30% held out for testing)
// MAGIC val Array(trainingData, testData) = training.randomSplit(Array(0.9, 0.1), seed = 1234L)
// MAGIC 
// MAGIC // Train a NaiveBayes model.
// MAGIC val model = new NaiveBayes()
// MAGIC   .fit(trainingData)
// MAGIC 
// MAGIC // Select example rows to display.
// MAGIC val predictions = model.transform(testData)
// MAGIC 
// MAGIC val predicted = predictions.select("features", "prediction", "label")
// MAGIC predicted.show()

// COMMAND ----------

// MAGIC %md ### Train a One-vs-Rest classifier (a.k.a. One-vs-All) Model
// MAGIC OneVsRest is an example of a machine learning reduction for performing multiclass classification given a base classifier that can perform binary classification efficiently. It is also known as “One-vs-All.”

// COMMAND ----------

import org.apache.spark.ml.classification.{LogisticRegression, OneVsRest}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

// load data file.
import org.apache.spark.ml.feature.VectorAssembler

val assembler = new VectorAssembler().setInputCols(Array("customerID_indexed", "gender_indexed", "SeniorCitizen", "Partner_indexed", "Dependents_indexed", "PhoneService_indexed", "MultipleLines_indexed", "InternetService_indexed", "OnlineSecurity_indexed", "OnlineBackup_indexed", "DeviceProtection_indexed", "TechSupport_indexed", "StreamingTV_indexed", "StreamingMovies_indexed", "Contract_indexed", "PaperlessBilling_indexed", "PaymentMethod_indexed", "tenure", "MonthlyCharges", "TotalCharges" )).setOutputCol("features")

val training = assembler.transform(TelDF).select($"features", $"Churn_indexed".alias("label"))

// generate the train/test split.
val Array(train, test) = training.randomSplit(Array(0.8, 0.2))

// instantiate the base classifier
val classifier = new LogisticRegression()
  .setMaxIter(10)
  .setTol(1E-6)
  .setFitIntercept(true)

// instantiate the One Vs Rest Classifier.
val ovr = new OneVsRest().setClassifier(classifier)

// train the multiclass model.
val ovrModel = ovr.fit(train)

// score the model on test data.
val predictions = ovrModel.transform(test)

val predicted = predictions.select("features", "prediction", "label")
predicted.show()


// COMMAND ----------

val evaluator = new MulticlassClassificationEvaluator()
  .setMetricName("accuracy")

// compute the classification error on test data.
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${1 - accuracy}")
