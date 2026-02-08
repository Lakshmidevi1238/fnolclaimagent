# **Autonomous FNOL Claim Processing Agent**





### A lightweight backend agent that processes First Notice of Loss (FNOL) documents and performs:



##### 

* ##### &nbsp;PDF text extraction
* ##### &nbsp;Field extraction
* ##### &nbsp;Validation of mandatory fields
* ##### &nbsp;Claim classification
* ##### &nbsp;Rule-based workflow routing
* ##### &nbsp;Reasoning explanation output







### **Problem Breakdown**



#### 

#### The FNOL processing pipeline is divided into deterministic stages:







#### **PDF → Text → Structured Fields → Validation → Classification → Routing → Reasoning**







### **Tech Stack**



* ##### **Java 17**
* ##### **Spring Boot**
* ##### **Apache PDFBox**
* ##### **Regex-based extraction**
* ##### **REST API**
* ##### **Postman testing**





### **Architecture Modules**

##### 

##### **Parser Layer**

##### Extracts structured fields from FNOL PDFs using layout-aware pattern extraction.

##### 

##### **Validation Layer**

##### Checks mandatory fields and detects missing or inconsistent values.

##### 

##### **Classification Layer**

##### Derives claim type using description keywords.

##### 

##### **Routing Engine**

##### Routes claims using priority rules:



##### **missing fields → MANUAL\_REVIEW**

##### **fraud keywords → INVESTIGATION**

##### **injury claims → SPECIALIST\_QUEUE**

##### **low damage (< 25K) → FAST\_TRACK**

##### **else → STANDARD\_QUEUE**









### **Approach \& Logic Design**





#### 

#### **Step 1 — Document Ingestion**

#### FNOL documents are uploaded through a REST endpoint.  

#### Apache PDFBox is used to extract the text layer from the PDF.

#### 

#### Design choice:

#### \- Use reliable text extraction.

##### 

#### **Step 2 — Field Extraction Strategy**





#### Instead of naive regex matching, a **label-anchored extraction approach** was used:

#### 

#### \- Patterns are anchored to FNOL form headings

#### \- Capture groups are restricted to same-line values

#### \- Post-filters remove form instruction text (like “INSURED’S MAILING ADDRESS”)

#### \- Person-name cleaner removes business keywords from name fields

#### 

#### Example protections implemented:

#### \- Prevent heading bleed into values

#### \- Reject all-caps label lines

#### \- Trim trailing field instructions

#### \- Stop capture at known label keywords

#### 

#### This made extraction robust across slightly different FNOL layouts.



##### 

#### **Step 3 — Structured Claim Model**

##### 

#### All extracted data is mapped into a structured `ClaimData` model:

#### 

#### \- Policy information

#### \- Incident information

#### \- Parties involved

#### \- Asset details

#### \- Financial estimate

#### \- Derived claim type

#### 

#### This allows validation and routing to operate on structured data instead of raw text.







#### **Step 4 — Validation Layer**

#### 

#### A dedicated validation service checks:

#### 

#### \- missing mandatory fields

#### \- format inconsistencies (example: invalid date)

#### \- incomplete asset or estimate data

#### 

#### Validation runs **before routing** and can override routing decisions.



#### 

#### **Step 5 — Priority-Based Routing Engine**

#### 

#### Routing is rule-driven with strict priority order to avoid conflicts:





##### **missing fields → MANUAL\_REVIEW**

##### **fraud keywords → INVESTIGATION**

##### **injury claims → SPECIALIST\_QUEUE**

##### **low damage (< 25K) → FAST\_TRACK**

##### **else → STANDARD\_QUEUE**









#### **Step 6 — Reasoning Output**

#### 

#### Every routing decision produces a short reasoning message, for example:

#### 

#### \- “Mandatory fields missing”

#### \- “Fraud indicators detected in description”

#### \- “Claim classified as injury-related”

#### \- “Low estimated damage — fast track eligible”

#### 

#### This makes the agent’s behavior auditable and transparent.







#### **Step 8 — Test Matrix Driven Development**

#### 

#### The system was verified using multiple FNOL test scenarios:

#### 

#### \- fast-track case

#### \- missing-field case

#### \- injury case

#### \- fraud case

#### \- high-damage standard case





#### Each scenario was tested through Postman with JSON output verification.





#### 













