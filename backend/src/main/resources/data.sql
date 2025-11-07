-- USER
INSERT INTO users (email, password, created_at)
VALUES ('user@mail.com', '$2a$10$2G58X38zKNefwC89swOx7eZNIaOhieIxNyP0v3l9BZPhKbix/2nLy', '2025-01-01');

-- EXAMPLE COURSE AND LEARNING PATH

-- Course
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'CFA® Level I Masterclass: Investment Foundations',
    'This comprehensive course is designed to help aspiring finance professionals prepare for the CFA Level I exam. Covering topics like ethical standards, financial reporting, and portfolio management, it blends theoretical concepts with practical insights. Ideal for those beginning their CFA journey or seeking a structured finance refresher.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1372,
    '2024-11-02',
    '2025-03-20'
);

INSERT INTO course_objectives (course_id, objective) VALUES
(1, 'Understand the core principles of financial reporting and their role in investment analysis.'),
(1, 'Explain the function and structure of financial markets within the global economy.'),
(1, 'Analyze time value of money concepts and apply them to valuation scenarios.'),
(1, 'Identify key ethical responsibilities in the investment profession using CFA Institute standards.'),
(1, 'Evaluate basic asset classes, including equities, fixed income, and derivatives, and their uses in portfolios.');

INSERT INTO course_requirements (course_id, requirements) VALUES
(1, 'A laptop or desktop computer with a stable internet connection'),
(1, 'A financial calculator (e.g., BA II Plus or HP 12C)'),
(1, 'The CFA institute handbook');

INSERT INTO course_creators (course_id, creator) VALUES (1, 'Sarah Kim');
INSERT INTO course_creators (course_id, creator) VALUES (1, 'Daniel Rivera');

-- Course Tags
INSERT INTO tags (tag) VALUES ('investing');
INSERT INTO tags (tag) VALUES ('ethics');
INSERT INTO tags (tag) VALUES ('portfolio management');
INSERT INTO tags (tag) VALUES ('financial analysis');
INSERT INTO tag_courses (course_id, tag_id) VALUES (1, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (1, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (1, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (1, 4);

-- Course Sections
INSERT INTO sections (id, title, description, position, course_id) VALUES
(1, 'Introduction to the CFA Program & Ethical Standards',
    'Get an overview of the CFA Program, exam structure, and a deep dive into ethics and professional standards—one of the most heavily weighted topics in Level I.',
    1, 1),
(2, 'Quantitative Methods & Financial Reporting',
    'Learn the fundamentals of time value of money, probability, and financial statement analysis. Key formulas and concepts are broken down in a practical, easy-to-follow manner.',
    2, 1),
(3, 'Economics & Corporate Finance',
    'Explore micro and macroeconomic principles, followed by corporate governance, capital budgeting, and cost of capital essentials.',
    3, 1),
(4, 'Portfolio Management & Equity Investments',
    'Conclude with core concepts in portfolio theory, risk-return frameworks, and detailed equity valuation methods.',
    4, 1);

-- Section Lessons
INSERT INTO lessons (id, title, description, position, section_id, content, duration) VALUES
(1, 'Welcome to the CFA® Program',
    'An overview of the CFA Program, its structure, and what to expect throughout the certification process.',
    1, 1, '', 20),
(2, 'Understanding the CFA® Institute Code of Ethics',
    'An introduction to the CFA Institute’s Code of Ethics and why it is central to the CFA curriculum.',
    2, 1, '', 45),
(3, 'Professional Standards and Guidance',
    'Detailed breakdown of the Standards of Professional Conduct, with examples and context.',
    3, 1, '', 185),
(4, 'Ethical Decision-Making Frameworks',
    'Tools and models to help candidates approach ethical dilemmas using consistent, principled reasoning.',
    4, 1, '', 50),
(5, 'Common Ethical Violations and Case Studies',
    'Real-world examples of ethical missteps and what we can learn from them in the context of finance.',
    5, 1, '', 140),
(6, 'Exam Strategy: Ethics and Professional Standards',
    'Tips for mastering ethics questions on the CFA Level I exam, including how to spot tricky language.',
    6, 1, '', 165);

INSERT INTO lessons (id, title, description, position, section_id, content, duration) VALUES
(7, 'Time Value of Money Basics',
    'Learn the foundational concepts of time value of money (TVM), including present and future value calculations.',
    7, 2, '', 40),
(8, 'Discounted Cash Flow Applications',
    'Explore real-world applications of DCF techniques in valuation and investment decisions.',
    8, 2, '', 55),
(9, 'Probability and Statistical Concepts',
    'Understand key probability rules, distributions, and statistical measures relevant to investment analysis.',
    9, 2, '', 30),
(10, 'Hypothesis Testing and Confidence Intervals',
    'Learn to test investment hypotheses and calculate confidence intervals for financial data.',
    10, 2, '', 80),
(11, 'Introduction to Financial Reporting and Analysis',
    'Get familiar with the structure and content of financial statements and the reporting process.',
    11, 2, '', 15),
(12, 'Income Statement and Balance Sheet Deep Dive',
    'Analyze key elements of income statements and balance sheets, with a focus on analyst-relevant metrics.',
    12, 2, '', 40),
(13, 'Cash Flow Statements and Financial Ratios',
    'Interpret cash flow statements and apply liquidity, solvency, and profitability ratios.',
    13, 2, '', 135);

INSERT INTO lessons (id, title, description, position, section_id, content, duration) VALUES
(14, 'Microeconomics for Finance Professionals',
    'Explore supply and demand, market structures, and the behavior of firms in different competitive environments.',
    14, 3, '', 10),
(15, 'Macroeconomic Principles and Indicators',
    'Understand GDP, inflation, interest rates, and how central banks influence financial markets.',
    15, 3, '', 25),
(16, 'Corporate Governance and ESG Considerations',
    'Review the principles of corporate governance and the growing importance of environmental, social, and governance (ESG) factors.',
    16, 3, '', 60),
(17, 'Capital Budgeting and Cost of Capital',
    'Learn how firms evaluate investment opportunities using NPV, IRR, and WACC methodologies.',
    17, 3, '', 110);

INSERT INTO lessons (id, title, description, position, section_id, content, duration) VALUES
(18, 'Introduction to Portfolio Management',
    'Understand the objectives and processes involved in constructing and managing investment portfolios.',
    18, 4, '', 10),
(19, 'Risk and Return Fundamentals',
    'Explore how risk and return are measured, and their relationship in portfolio theory.',
    19, 4, '', 140),
(20, 'Modern Portfolio Theory (MPT)',
    'Dive into key concepts like diversification, efficient frontiers, and the Capital Market Line.',
    20, 4, '', 30),
(21, 'Equity Markets and Instruments',
    'Review types of equity securities, stock exchanges, and the trading mechanisms investors use.',
    21, 4, '', 200),
(22, 'Equity Valuation Techniques',
    'Analyze equity valuation models including DDM, P/E ratios, and price-to-book approaches.',
    22, 4, '', 125);

-- Lesson Content
UPDATE lessons SET content = '
# Time Value of Money Basics

Welcome to the first lesson in the *Quantitative Methods & Financial Reporting* section of the **CFA® Level I Masterclass: Investment Foundations**.

Understanding the **Time Value of Money (TVM)** is critical in financial analysis, valuation, and investment decision-making. This concept underpins everything from bond pricing to discounted cash flow models.

---

## What Is the Time Value of Money?

The Time Value of Money is the idea that money available today is worth more than the same amount in the future due to its potential earning capacity.

In simple terms:

> A dollar today is more valuable than a dollar tomorrow.

This principle reflects the **opportunity cost** of capital — money can earn interest, be invested, or lose value due to inflation over time.

---

## Core TVM Formulas

There are four primary TVM formulas used in CFA Level I:

### 1. Future Value (FV)

FV = PV × (1 + r)^n


- `PV` = Present Value
- `r` = interest rate per period
- `n` = number of periods

### 2. Present Value (PV)

PV = FV / (1 + r)^n


These formulas apply for both **lump sum** and **annuity** cash flows.

---

## Example Problem

Suppose you invest **$1,000** in a savings account that pays **5% annual interest**, compounded annually, for **3 years**.

What is the future value?

FV = 1000 × (1 + 0.05)^3
FV = 1000 × 1.157625 = $1,157.63


Thus, your investment grows to **$1,157.63** after 3 years.

---

## Concept Check

- Why is present value always **less** than future value?
- What happens to FV if the interest rate increases?
- How would monthly compounding change the formula?

Try to reflect on these questions before moving on.

---

## Summary

- TVM reflects the principle that money today is more valuable than money tomorrow.
- It’s used to calculate the present or future value of cash flows.
- You must understand TVM to apply discounting and compounding in finance.

---

Next up: *Discounted Cash Flow Applications* — we''ll apply these concepts to discounted cash flows.'

WHERE id = 7;


UPDATE lessons SET content =
'# Discounted Cash Flow Applications

In this lesson, we dive into **Discounted Cash Flow (DCF)** techniques, a core tool in valuation and investment analysis.

---

## What Is Discounted Cash Flow?

Discounted Cash Flow is a valuation method that estimates the value of an investment based on its expected future cash flows, adjusted for the time value of money.

By discounting these cash flows back to their present value, investors can assess whether an investment is worthwhile.

---

## Steps in a DCF Analysis

1. **Forecast the expected future cash flows** over a specific period.
2. **Determine the appropriate discount rate**, often the weighted average cost of capital (WACC).
3. **Calculate the present value** of each cash flow using the discount rate.
4. **Sum the present values** to find the total intrinsic value.

---

## The Basic DCF Formula

The general formula to calculate the present value of cash flows is:

PV = \sum_{t=1}^{n} \frac{CF_t}{(1 + r)^t}


Where:
- `CF_t` = cash flow at time `t`
- `r` = discount rate
- `n` = number of periods

---

## Example Application

Imagine a project expected to generate the following cash flows over 3 years:

| Year | Cash Flow (USD) |
|-------|----------------|
| 1     | 10,000         |
| 2     | 12,000         |
| 3     | 15,000         |

Assuming a discount rate of 8%, the present value of these cash flows is:

PV = \frac{10,000}{(1 + 0.08)^1} + \frac{12,000}{(1 + 0.08)^2} + \frac{15,000}{(1 + 0.08)^3}


Calculating each term:

PV = 9,259.26 + 10,288.07 + 11,915.18 = 31,462.51


---

## Concept Check

- How does an increase in the discount rate affect the present value of future cash flows?
- Why is the discount rate often considered the project’s risk level?
- How would cash flows beyond the forecast period be valued?

---

## Summary

- DCF helps investors estimate intrinsic value based on expected cash flows and discounting.
- Accurate forecasts and appropriate discount rates are essential.
- DCF is widely used in equity valuation, project finance, and capital budgeting.

---

Next lesson: *Probability and Statistical Concepts* — diving into the basics of probability theory and statistics.'
WHERE id = 8;


UPDATE lessons SET content =
'# Probability and Statistical Concepts

In this lesson, we introduce the fundamental concepts of probability and statistics, essential for making informed decisions under uncertainty.

---

## What Is Probability?

Probability measures the likelihood that a particular event will occur, expressed as a number between 0 and 1.

- A probability of 0 means the event will never occur.
- A probability of 1 means the event is certain.

---

## Types of Probability

- **Theoretical Probability:** Based on reasoning or calculation (e.g., flipping a fair coin).
- **Empirical Probability:** Based on observed data or experiments.
- **Subjective Probability:** Based on personal judgment or experience.

---

## Basic Probability Rules

- The sum of all possible outcomes’ probabilities equals 1.
- The probability of the complement of an event \(A\) is:

P(A^c) = 1 - P(A)


- For two mutually exclusive events \(A\) and \(B\):

P(A \cup B) = P(A) + P(B)


---

## Random Variables and Distributions

A random variable assigns numerical values to outcomes of a random process.

- **Discrete Random Variables:** Take countable values (e.g., number of heads in coin tosses).
- **Continuous Random Variables:** Take values in a continuum (e.g., stock prices).

Common distributions include the Binomial, Normal, and Uniform distributions.

---

## Descriptive Statistics

Key measures to summarize data include:

- **Mean (Average):** Central tendency of data.
- **Variance and Standard Deviation:** Measure of data dispersion.
- **Skewness and Kurtosis:** Describe the shape of the distribution.

---

## Concept Check

- What is the difference between theoretical and empirical probability?
- How does increasing variance affect the spread of data?
- Why is the Normal distribution important in finance?

---

## Summary

- Probability helps quantify uncertainty in financial decisions.
- Understanding distributions and descriptive statistics is critical for data analysis.
- These concepts form the basis for risk assessment and modeling.

---

Next lesson: *Hypothesis Testing and Confidence Intervals* — learning how to test assumptions and estimate parameters.'
WHERE id = 9;


UPDATE lessons SET content =
'# Hypothesis Testing and Confidence Intervals

This lesson covers the fundamentals of hypothesis testing and confidence intervals, key tools in statistical inference.

---

## What Is Hypothesis Testing?

Hypothesis testing is a method used to make decisions or inferences about population parameters based on sample data.

- The **null hypothesis (H0)** represents the status quo or no effect.
- The **alternative hypothesis (H1)** represents the claim we want to test.

---

## Steps in Hypothesis Testing

1. Formulate the null and alternative hypotheses.
2. Choose a significance level (\(\alpha\)), commonly 0.05.
3. Calculate the test statistic from the sample data.
4. Determine the critical value or p-value.
5. Decide to reject or fail to reject the null hypothesis.

---

## Types of Errors

- **Type I Error:** Rejecting a true null hypothesis (false positive).
- **Type II Error:** Failing to reject a false null hypothesis (false negative).

---

## Confidence Intervals

A confidence interval gives a range of values within which the true population parameter is expected to lie, with a specified level of confidence (e.g., 95%).

The general form for a confidence interval for a population mean is:

\bar{x} \pm z_{\alpha/2} \times \frac{\sigma}{\sqrt{n}}


Where:
- \(\bar{x}\) = sample mean
- \(z_{\alpha/2}\) = critical value from the standard normal distribution
- \(\sigma\) = population standard deviation (or estimate)
- \(n\) = sample size

---

## Example

Suppose a sample mean of 100 with a standard deviation of 15 from 36 observations is used to calculate a 95% confidence interval:

100 \pm 1.96 \times \frac{15}{\sqrt{36}} = 100 \pm 4.9


The confidence interval is from 95.1 to 104.9.

---

## Concept Check

- What does a 95% confidence level mean in practice?
- How do Type I and Type II errors affect decision-making?
- Why is sample size important for confidence intervals?

---

## Summary

- Hypothesis testing helps evaluate claims about populations using sample data.
- Confidence intervals provide ranges that likely contain population parameters.
- Understanding these concepts is critical for sound financial analysis.

---

Next lesson: *Introduction to Financial Reporting and Analysis* — where we explore the fundamentals of financial statements and analysis.'
WHERE id = 10;


UPDATE lessons SET content =
'# Introduction to Financial Reporting and Analysis

This lesson introduces the basics of financial reporting and analysis, which are essential for interpreting company performance and making investment decisions.

---

## What Is Financial Reporting?

Financial reporting involves the disclosure of financial information to stakeholders through financial statements, providing transparency about a company’s financial health.

Key reports include the balance sheet, income statement, and cash flow statement.

---

## The Purpose of Financial Analysis

Financial analysis evaluates financial data to understand the company’s performance, liquidity, profitability, and solvency.

It helps investors, creditors, and management make informed decisions.

---

## Key Financial Statements

### 1. Balance Sheet

Shows a company’s assets, liabilities, and shareholders’ equity at a specific point in time.

### 2. Income Statement

Reports revenues, expenses, and profits over a period.

### 3. Cash Flow Statement

Tracks the inflow and outflow of cash, categorized into operating, investing, and financing activities.

---

## Users of Financial Reports

- **Investors** assess profitability and risk.
- **Creditors** evaluate creditworthiness.
- **Management** monitors performance and plans strategy.
- **Regulators** ensure compliance and transparency.

---

## Accounting Principles and Standards

Financial reporting follows standards such as GAAP or IFRS, ensuring consistency and comparability across companies.

---

## Concept Check

- Why is the balance sheet referred to as a “snapshot”?
- How does the income statement differ from the cash flow statement?
- What role do accounting standards play in financial reporting?

---

## Summary

- Financial reporting provides vital information through standardized statements.
- Analysis of these reports aids various stakeholders in decision-making.
- A strong grasp of financial statements is foundational for all finance professionals.

---

Next lesson: *Income Statement and Balance Sheet Deep Dive* — exploring detailed components and analysis techniques.'
WHERE id = 11;


UPDATE lessons SET content =
'# Income Statement and Balance Sheet Deep Dive

In this lesson, we take a closer look at the two fundamental financial statements: the income statement and the balance sheet.

---

## Income Statement: Detailed View

The income statement summarizes a company’s revenues and expenses over a specific period, showing net profit or loss.

### Key Components:
- **Revenue:** Income earned from primary business activities.
- **Cost of Goods Sold (COGS):** Direct costs attributable to production.
- **Gross Profit:** Revenue minus COGS.
- **Operating Expenses:** Costs not directly tied to production (e.g., SG&A).
- **Net Income:** Profit after all expenses, taxes, and interest.

---

## Balance Sheet: Detailed View

The balance sheet provides a snapshot of a company’s financial position at a specific date.

### Key Sections:
- **Assets:** Resources owned (current and non-current).
- **Liabilities:** Obligations owed (current and long-term).
- **Shareholders’ Equity:** Residual interest after liabilities.

The fundamental accounting equation is:

Assets = Liabilities + Shareholders’ Equity


---

## Importance of Understanding Both Statements

- Income statement shows profitability over time.
- Balance sheet shows financial health at a point in time.
- Together, they provide a comprehensive picture of a company’s financial performance.

---

## Common Analysis Techniques

- Vertical and horizontal analysis
- Ratio analysis (profitability, liquidity, leverage)
- Trend analysis

---

## Concept Check

- How does depreciation affect the income statement and balance sheet?
- Why are current and non-current classifications important?
- How can net income differ from cash flow?

---

## Summary

- Deep understanding of income statements and balance sheets is vital for financial analysis.
- These statements are interconnected and provide complementary insights.
- Mastery of their components enables better investment and management decisions.

---

Next lesson: *Cash Flow Statements and Financial Ratios* — learning how cash flows and ratios inform company valuation and health.'
WHERE id = 12;


UPDATE lessons SET content =
'# Cash Flow Statements and Financial Ratios

This lesson focuses on cash flow statements and key financial ratios, crucial tools for analyzing a company’s liquidity, profitability, and overall financial health.

---

## Cash Flow Statement Overview

The cash flow statement shows the actual inflows and outflows of cash during a period, categorized into three sections:

- **Operating Activities:** Cash flows from core business operations.
- **Investing Activities:** Cash flows from buying or selling long-term assets.
- **Financing Activities:** Cash flows related to borrowing, repaying debt, and equity financing.

---

## Why Cash Flow Matters

Profitability doesn’t always translate into cash availability. The cash flow statement helps assess a company’s ability to generate cash and meet its obligations.

---

## Key Financial Ratios

Financial ratios are metrics used to evaluate various aspects of a company’s performance:

### Liquidity Ratios

- **Current Ratio:**

Current Ratio = \frac{Current Assets}{Current Liabilities}


- **Quick Ratio:**

Quick Ratio = \frac{Current Assets - Inventory}{Current Liabilities}


### Profitability Ratios

- **Return on Assets (ROA):**

ROA = \frac{Net Income}{Total Assets}


- **Return on Equity (ROE):**

ROE = \frac{Net Income}{Shareholders’ Equity}


### Leverage Ratios

- **Debt to Equity Ratio:**

Debt to Equity = \frac{Total Debt}{Shareholders’ Equity}


---

## Concept Check

- How can a company be profitable but have poor cash flow?
- Why are liquidity ratios important for short-term financial health?
- How do leverage ratios affect risk and return?

---

## Summary

- Cash flow statements provide insights beyond profitability.
- Financial ratios help compare companies and assess strengths and weaknesses.
- Together, they form a vital part of financial analysis and decision-making.

---

This concludes the *CFA® Level I Masterclass: Investment Foundations* course.

Thank you for learning with us!'
WHERE id = 13;


-- Questions
INSERT INTO questions (id, lesson_id, question_text, type, is_correct, lesson_number, core)
VALUES
(1, 7, 'The time value of money concept states that a dollar today is worth more than a dollar in the future.', 'TRUE_FALSE', TRUE, 1, true),
(2, 7, 'Receiving $100 one year from now is financially equivalent to receiving $100 today.', 'TRUE_FALSE', FALSE, 2, true),
(3, 7, 'The present value of money increases as the interest rate increases.', 'TRUE_FALSE', FALSE, 3, true),
(4, 7, 'Discounting is the process of finding the present value of a future amount.', 'TRUE_FALSE', TRUE, 4, false),
(5, 7, 'Compound interest earns interest on both the initial principal and the accumulated interest.', 'TRUE_FALSE', TRUE, 5, false),
(6, 7, 'The future value of money is always less than its present value.', 'TRUE_FALSE', FALSE, 6, false);

INSERT INTO questions (id, lesson_id, question_text, type, is_correct, lesson_number, core)
VALUES
(7, 8, 'Discounted cash flow (DCF) valuation involves forecasting future cash flows and discounting them to the present.', 'TRUE_FALSE', TRUE, 1, true),
(8, 8, 'DCF analysis is not affected by the chosen discount rate.', 'TRUE_FALSE', FALSE, 2, true),
(9, 8, 'The net present value (NPV) of an investment should be positive for it to be considered financially viable.', 'TRUE_FALSE', TRUE, 3, false),
(10, 8, 'Future cash flows are always discounted using the inflation rate.', 'TRUE_FALSE', FALSE, 4, false),
(11, 8, 'A higher discount rate results in a lower present value of future cash flows.', 'TRUE_FALSE', TRUE, 5, false);

INSERT INTO questions (id, lesson_id, question_text, type, is_correct, lesson_number, core)
VALUES
(12, 9, 'The probability of all possible outcomes in a sample space always sums to 1.', 'TRUE_FALSE', TRUE, 1, true),
(13, 9, 'The standard deviation measures the central tendency of a dataset.', 'TRUE_FALSE', FALSE, 2, true),
(14, 9, 'An event with a probability of 0.5 is equally likely to occur as not occur.', 'TRUE_FALSE', TRUE, 3, true),
(15, 9, 'Mutually exclusive events can occur at the same time.', 'TRUE_FALSE', FALSE, 4, false),
(16, 9, 'A normal distribution is symmetric around its mean.', 'TRUE_FALSE', TRUE, 5, false);

INSERT INTO questions (id, lesson_id, question_text, type, is_correct, lesson_number, core)
VALUES
(17, 10, 'A Type I error occurs when a true null hypothesis is incorrectly rejected.', 'TRUE_FALSE', TRUE, 1, true),
(18, 10, 'The confidence level and significance level add up to 1.', 'TRUE_FALSE', TRUE, 2, true),
(19, 10, 'A p-value greater than the significance level leads to rejecting the null hypothesis.', 'TRUE_FALSE', FALSE, 3, true),
(20, 10, 'A 95% confidence interval means we are 95% confident the population parameter lies within the interval.', 'TRUE_FALSE', TRUE, 4, false),
(21, 10, 'Hypothesis testing is only useful in qualitative analysis.', 'TRUE_FALSE', FALSE, 5, false),
(22, 10, 'A smaller sample size increases the width of a confidence interval.', 'TRUE_FALSE', TRUE, 6, false);

INSERT INTO questions (id, lesson_id, question_text, type, is_correct, lesson_number, core)
VALUES
(23, 11, 'Financial reporting provides information useful to investors, lenders, and other stakeholders.', 'TRUE_FALSE', TRUE, 1, true),
(24, 11, 'The balance sheet and income statement are examples of financial reports.', 'TRUE_FALSE', TRUE, 2, true),
(25, 11, 'All financial reports are audited by government agencies.', 'TRUE_FALSE', FALSE, 3, false),
(26, 11, 'The purpose of financial analysis is to evaluate a company’s performance and financial health.', 'TRUE_FALSE', TRUE, 4, false),
(27, 11, 'GAAP and IFRS are the only financial reporting standards globally.', 'TRUE_FALSE', FALSE, 5, false);

INSERT INTO questions (id, lesson_id, question_text, type, is_correct, lesson_number, core)
VALUES
(28, 12, 'The income statement shows revenues and expenses over a period of time.', 'TRUE_FALSE', TRUE, 1, true),
(29, 12, 'Assets must always equal liabilities minus equity.', 'TRUE_FALSE', FALSE, 2, true),
(30, 12, 'The balance sheet reflects a company’s financial position at a specific point in time.', 'TRUE_FALSE', TRUE, 3, true),
(31, 12, 'Net income is found on the balance sheet.', 'TRUE_FALSE', FALSE, 4, false),
(32, 12, 'Retained earnings appear on the equity section of the balance sheet.', 'TRUE_FALSE', TRUE, 5, false),
(33, 12, 'Total liabilities and equity should equal total assets.', 'TRUE_FALSE', TRUE, 6, false);

INSERT INTO questions (id, lesson_id, question_text, type, is_correct, lesson_number, core)
VALUES
(34, 13, 'The cash flow statement shows cash inflows and outflows from operating, investing, and financing activities.', 'TRUE_FALSE', TRUE, 1, true),
(35, 13, 'Depreciation is considered a cash outflow.', 'TRUE_FALSE', FALSE, 2, true),
(36, 13, 'A high current ratio generally indicates strong liquidity.', 'TRUE_FALSE', TRUE, 3, false),
(37, 13, 'The cash flow statement does not account for non-cash expenses.', 'TRUE_FALSE', FALSE, 4, false),
(38, 13, 'Financial ratios help evaluate a firm’s profitability, liquidity, and solvency.', 'TRUE_FALSE', TRUE, 5, false);

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (39, 7, 'Which of the following best describes the time value of money?', 'MULTIPLE_CHOICE', 7, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(1, FALSE, 39, 'Money loses value because of inflation alone.'),
(2, TRUE, 39, 'A dollar today is worth more than a dollar in the future.'),
(3, FALSE, 39, 'The value of money remains constant over time.'),
(4, FALSE, 39, 'Money is only valuable when invested.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (40, 7, 'What does "discounting" refer to in time value of money?', 'MULTIPLE_CHOICE', 8, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(5, TRUE, 40, 'The process of finding the present value of a future sum.'),
(6, FALSE, 40, 'Increasing the value of money over time.'),
(7, FALSE, 40, 'Calculating taxes on interest earned.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (41, 7, 'Which of the following affects the present value of a future cash flow?', 'MULTIPLE_CHOICE', 9, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(8, FALSE, 41, 'The company’s branding.'),
(9, TRUE, 41, 'The discount rate used.'),
(10, FALSE, 41, 'The number of employees.'),
(11, FALSE, 41, 'Customer satisfaction ratings.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (42, 7, 'Why is money today more valuable than money in the future?', 'MULTIPLE_CHOICE', 10, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(12, FALSE, 42, 'Because of reduced supply of currency.'),
(13, FALSE, 42, 'Because taxes are lower now.'),
(14, TRUE, 42, 'Because it can be invested to earn interest.'),
(15, FALSE, 42, 'Because future inflation increases purchasing power.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (43, 7, 'Which formula is commonly used to calculate future value?', 'MULTIPLE_CHOICE', 11, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(16, TRUE, 43, 'FV = PV × (1 + r)^n'),
(17, FALSE, 43, 'PV = FV + r × n'),
(18, FALSE, 43, 'FV = PV ÷ r'),
(19, FALSE, 43, 'FV = (PV × r) - n');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (44, 8, 'What is the primary purpose of discounted cash flow (DCF) analysis?', 'MULTIPLE_CHOICE', 6, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(20, TRUE, 44, 'To estimate the present value of future cash flows.'),
(21, FALSE, 44, 'To calculate net income.'),
(22, FALSE, 44, 'To evaluate market share.'),
(23, FALSE, 44, 'To forecast interest rates.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (45, 8, 'Which component is NOT typically required for a DCF analysis?', 'MULTIPLE_CHOICE', 7, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(24, FALSE, 45, 'Future cash flow projections.'),
(25, FALSE, 45, 'Discount rate.'),
(26, TRUE, 45, 'Company logo.'),
(27, FALSE, 45, 'Terminal value.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (46, 8, 'How does an increase in the discount rate affect the present value of future cash flows?', 'MULTIPLE_CHOICE', 8, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(28, FALSE, 46, 'It increases the present value.'),
(29, TRUE, 46, 'It decreases the present value.'),
(30, FALSE, 46, 'It has no effect.'),
(31, FALSE, 46, 'It doubles the future value.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (47, 8, 'What does the term "terminal value" refer to in a DCF model?', 'MULTIPLE_CHOICE', 9, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(32, FALSE, 47, 'The cash inflows for the first year only.'),
(33, TRUE, 47, 'The estimated value of a business beyond the forecast period.'),
(34, FALSE, 47, 'The net income minus depreciation.'),
(35, FALSE, 47, 'The final dividend payment.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (48, 8, 'Which of the following is most likely to increase the valuation in a DCF model?', 'MULTIPLE_CHOICE', 10, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(36, TRUE, 48, 'Higher projected cash flows.'),
(37, FALSE, 48, 'Lower projected cash flows.'),
(38, FALSE, 48, 'Increasing the discount rate.'),
(39, FALSE, 48, 'Omitting terminal value.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (49, 8, 'Why is sensitivity analysis important in DCF models?', 'MULTIPLE_CHOICE', 11, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(40, TRUE, 49, 'To test how changes in assumptions impact valuation.'),
(41, FALSE, 49, 'To avoid forecasting cash flows.'),
(42, FALSE, 49, 'To predict competitor behavior.'),
(43, FALSE, 49, 'To eliminate risk entirely.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (50, 9, 'What is the maximum possible value of a probability?', 'MULTIPLE_CHOICE', 6, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(44, FALSE, 50, '0.5'),
(45, FALSE, 50, '0.75'),
(46, TRUE, 50, '1'),
(47, FALSE, 50, '10');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (51, 9, 'What does a probability of 0 mean?', 'MULTIPLE_CHOICE', 7, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(48, FALSE, 51, 'The event is very likely.'),
(49, TRUE, 51, 'The event is impossible.'),
(50, FALSE, 51, 'The event is certain.'),
(51, FALSE, 51, 'There is a 50% chance the event will occur.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (52, 9, 'Which of the following describes a normal distribution?', 'MULTIPLE_CHOICE', 8, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(52, TRUE, 52, 'It is symmetric and bell-shaped.'),
(53, FALSE, 52, 'It is skewed to the left.'),
(54, FALSE, 52, 'It has two peaks.'),
(55, FALSE, 52, 'It has a flat top.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (53, 9, 'What does the standard deviation measure?', 'MULTIPLE_CHOICE', 9, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(56, FALSE, 53, 'The average value of a dataset.'),
(57, FALSE, 53, 'The sum of all data points.'),
(58, TRUE, 53, 'The dispersion of values around the mean.'),
(59, FALSE, 53, 'The number of observations.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (54, 9, 'Which of the following is true about mutually exclusive events?', 'MULTIPLE_CHOICE', 10, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(60, TRUE, 54, 'They cannot occur at the same time.'),
(61, FALSE, 54, 'They are always independent.'),
(62, FALSE, 54, 'They have equal probabilities.'),
(63, FALSE, 54, 'They always sum to 1.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (55, 9, 'If the probability of A is 0.3 and the probability of B is 0.4, what is the maximum possible value of P(A or B)?', 'MULTIPLE_CHOICE', 11, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(64, FALSE, 55, '0.4'),
(65, FALSE, 55, '0.5'),
(66, FALSE, 55, '0.6'),
(67, TRUE, 55, '0.7');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (56, 10, 'What is the null hypothesis in statistical testing?', 'MULTIPLE_CHOICE', 7, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(68, TRUE, 56, 'A statement that there is no effect or no difference.'),
(69, FALSE, 56, 'A statement that the sample is biased.'),
(70, FALSE, 56, 'The conclusion reached when a test is significant.'),
(71, FALSE, 56, 'A prediction about future trends.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (57, 10, 'What does a p-value represent?', 'MULTIPLE_CHOICE', 8, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(72, FALSE, 57, 'The probability that the null hypothesis is true.'),
(73, TRUE, 57, 'The probability of observing the data assuming the null hypothesis is true.'),
(74, FALSE, 57, 'The power of the test.'),
(75, FALSE, 57, 'The confidence level of the sample.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (58, 10, 'If the p-value is less than the significance level, what is the appropriate decision?', 'MULTIPLE_CHOICE', 9, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(76, TRUE, 58, 'Reject the null hypothesis.'),
(77, FALSE, 58, 'Accept the null hypothesis.'),
(78, FALSE, 58, 'Decrease the sample size.'),
(79, FALSE, 58, 'Conduct another test immediately.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (59, 10, 'What does a 95% confidence interval mean?', 'MULTIPLE_CHOICE', 10, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(80, TRUE, 59, 'There is a 95% chance the interval contains the population parameter.'),
(81, FALSE, 59, '95% of the data lies within the interval.'),
(82, FALSE, 59, 'The sample mean is within 95% of the population mean.'),
(83, FALSE, 59, 'You will get the same result 95% of the time.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (60, 10, 'What is a Type I error?', 'MULTIPLE_CHOICE', 11, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(84, TRUE, 60, 'Rejecting the null hypothesis when it is actually true.'),
(85, FALSE, 60, 'Accepting the alternative hypothesis when it is false.'),
(86, FALSE, 60, 'Failing to reject the null when it is false.'),
(87, FALSE, 60, 'Using the wrong statistical test.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (61, 10, 'Which factor increases the width of a confidence interval?', 'MULTIPLE_CHOICE', 12, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(88, TRUE, 61, 'Smaller sample size.'),
(89, FALSE, 61, 'Lower variability.'),
(90, FALSE, 61, 'Higher confidence level.'),
(91, FALSE, 61, 'Larger sample size.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (62, 11, 'What is the primary purpose of financial reporting?', 'MULTIPLE_CHOICE', 6, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(92, TRUE, 62, 'To provide useful information to investors and creditors.'),
(93, FALSE, 62, 'To estimate tax liabilities.'),
(94, FALSE, 62, 'To prepare payroll reports.'),
(95, FALSE, 62, 'To track customer satisfaction.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (63, 11, 'Which of the following is a core financial statement?', 'MULTIPLE_CHOICE', 7, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(96, FALSE, 63, 'Audit report.'),
(97, FALSE, 63, 'Management forecast.'),
(98, TRUE, 63, 'Balance sheet.'),
(99, FALSE, 63, 'Press release.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (64, 11, 'Which financial statement shows a company’s financial position at a specific point in time?', 'MULTIPLE_CHOICE', 8, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(100, TRUE, 64, 'Balance sheet.'),
(101, FALSE, 64, 'Income statement.'),
(102, FALSE, 64, 'Statement of cash flows.'),
(103, FALSE, 64, 'Statement of retained earnings.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (65, 11, 'What is the accounting equation?', 'MULTIPLE_CHOICE', 9, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(104, FALSE, 65, 'Assets = Liabilities – Equity'),
(105, TRUE, 65, 'Assets = Liabilities + Equity'),
(106, FALSE, 65, 'Revenue = Expenses + Profit'),
(107, FALSE, 65, 'Cash = Income – Expenses');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (66, 11, 'Which organization is primarily responsible for setting accounting standards in the United States?', 'MULTIPLE_CHOICE', 10, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(108, FALSE, 66, 'SEC'),
(109, TRUE, 66, 'FASB'),
(110, FALSE, 66, 'IRS'),
(111, FALSE, 66, 'PCAOB');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (67, 11, 'Which of the following would most likely appear in the notes to the financial statements?', 'MULTIPLE_CHOICE', 11, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(112, TRUE, 67, 'Details about accounting policies used.'),
(113, FALSE, 67, 'Competitor financials.'),
(114, FALSE, 67, 'Daily stock price changes.'),
(115, FALSE, 67, 'Marketing strategies.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (68, 12, 'What does the income statement primarily report?', 'MULTIPLE_CHOICE', 7, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(116, TRUE, 68, 'A company’s revenues and expenses over a period of time.'),
(117, FALSE, 68, 'The financial position at a point in time.'),
(118, FALSE, 68, 'Cash inflows and outflows.'),
(119, FALSE, 68, 'Shareholder equity changes.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (69, 12, 'Which of the following is reported on the balance sheet?', 'MULTIPLE_CHOICE', 8, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(120, TRUE, 69, 'Assets, liabilities, and equity.'),
(121, FALSE, 69, 'Revenue and net income.'),
(122, FALSE, 69, 'Operating cash flows.'),
(123, FALSE, 69, 'Depreciation expense.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (70, 12, 'Where would you typically find "cost of goods sold"?', 'MULTIPLE_CHOICE', 9, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(124, TRUE, 70, 'Income statement.'),
(125, FALSE, 70, 'Balance sheet.'),
(126, FALSE, 70, 'Cash flow statement.'),
(127, FALSE, 70, 'Statement of changes in equity.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (71, 12, 'Which of the following increases equity on the balance sheet?', 'MULTIPLE_CHOICE', 10, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(128, FALSE, 71, 'Paying off a liability.'),
(129, TRUE, 71, 'Retaining net income.'),
(130, FALSE, 71, 'Purchasing inventory.'),
(131, FALSE, 71, 'Depreciating an asset.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (72, 12, 'What does “current” in current assets or liabilities refer to?', 'MULTIPLE_CHOICE', 11, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(132, TRUE, 72, 'Expected to be settled or used within one year.'),
(133, FALSE, 72, 'Items with no fixed value.'),
(134, FALSE, 72, 'Assets held for over five years.'),
(135, FALSE, 72, 'Items related to foreign transactions.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (73, 12, 'Which of the following is subtracted from revenue to calculate gross profit?', 'MULTIPLE_CHOICE', 12, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(136, TRUE, 73, 'Cost of goods sold.'),
(137, FALSE, 73, 'Depreciation.'),
(138, FALSE, 73, 'Taxes.'),
(139, FALSE, 73, 'Dividends.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (74, 13, 'Which section of the cash flow statement shows cash generated from normal business operations?', 'MULTIPLE_CHOICE', 6, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(140, TRUE, 74, 'Operating activities.'),
(141, FALSE, 74, 'Investing activities.'),
(142, FALSE, 74, 'Financing activities.'),
(143, FALSE, 74, 'Equity activities.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (75, 13, 'What does the current ratio measure?', 'MULTIPLE_CHOICE', 7, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(144, TRUE, 75, 'Ability to pay short-term obligations with current assets.'),
(145, FALSE, 75, 'Profitability of the company.'),
(146, FALSE, 75, 'Efficiency in using assets.'),
(147, FALSE, 75, 'Debt to equity balance.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (76, 13, 'Which of the following is a liquidity ratio?', 'MULTIPLE_CHOICE', 8, true);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(148, TRUE, 76, 'Quick ratio.'),
(149, FALSE, 76, 'Return on equity.'),
(150, FALSE, 76, 'Gross margin.'),
(151, FALSE, 76, 'Debt to asset ratio.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (77, 13, 'What is included in the financing activities section of the cash flow statement?', 'MULTIPLE_CHOICE', 9, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(152, TRUE, 77, 'Issuing or repaying debt and equity.'),
(153, FALSE, 77, 'Purchasing equipment.'),
(154, FALSE, 77, 'Collecting customer payments.'),
(155, FALSE, 77, 'Paying salaries.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (78, 13, 'Which ratio measures profitability?', 'MULTIPLE_CHOICE', 10, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(156, TRUE, 78, 'Return on assets (ROA).'),
(157, FALSE, 78, 'Current ratio.'),
(158, FALSE, 78, 'Quick ratio.'),
(159, FALSE, 78, 'Debt to equity ratio.');

INSERT INTO questions (id, lesson_id, question_text, type, lesson_number, core)
VALUES (79, 13, 'Why is the cash flow statement important?', 'MULTIPLE_CHOICE', 11, false);
INSERT INTO choices (id, is_correct, question_id, choice_text)
VALUES
(160, TRUE, 79, 'It shows how cash is generated and used during a period.'),
(161, FALSE, 79, 'It reports net income.'),
(162, FALSE, 79, 'It calculates earnings per share.'),
(163, FALSE, 79, 'It shows long-term liabilities.');


-- Learning Path
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'CFA® Starter Path: From Fundamentals to Level I',
    'This learning path is designed to guide aspiring analysts through the essential concepts needed before and during CFA Level I preparation. It starts with foundational finance and accounting, followed by more advanced CFA-specific material. Ideal for students or professionals building their investment finance expertise from the ground up.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1511883040705-6011fad9edfc?q=80&w=1748&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    843,
    '2024-10-10',
    '2025-02-25'
);

-- Learning Path Tags
INSERT INTO tags (tag) VALUES ('banking');
INSERT INTO tags (tag) VALUES ('career development');
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (1, 3);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (1, 5);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (1, 6);

-- PLACEHOLDER DATA

-- Tags

INSERT INTO tags (tag) VALUES ('budgeting');
INSERT INTO tags (tag) VALUES ('accounting');
INSERT INTO tags (tag) VALUES ('tax planning');
INSERT INTO tags (tag) VALUES ('savings');
INSERT INTO tags (tag) VALUES ('credit scores');
INSERT INTO tags (tag) VALUES ('loans');
INSERT INTO tags (tag) VALUES ('interest rates');
INSERT INTO tags (tag) VALUES ('banking basics');
INSERT INTO tags (tag) VALUES ('wealth management');
INSERT INTO tags (tag) VALUES ('stock market');
INSERT INTO tags (tag) VALUES ('cryptocurrency');
INSERT INTO tags (tag) VALUES ('debt management');
INSERT INTO tags (tag) VALUES ('insurance');
INSERT INTO tags (tag) VALUES ('inflation');

-- Courses

INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'CFA® Level I Masterclass: Investment Foundations',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum euismod, sem ut tincidunt viverra, turpis purus facilisis est, nec eleifend lorem lacus vel sapien. Morbi vitae tellus a turpis iaculis luctus. Curabitur at ligula ac nibh cursus vehicula. Aliquam erat volutpat. Nulla facilisi. Sed at justo ut sapien malesuada rhoncus. Cras suscipit felis a diam euismod, ac cursus nulla semper.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    873,
    '2024-07-14',
    '2025-01-09'
);
INSERT INTO course_creators (course_id, creator) VALUES (2, 'John Doe');
INSERT INTO course_creators (course_id, creator) VALUES (2, 'Ava Thompson');
INSERT INTO tag_courses (course_id, tag_id) VALUES (2, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (2, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (2, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (2, 18);

-- Course ID: 3
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Quantitative Methods for CFA® Level I: Core Concepts & Practice',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dignissim enim at nisi posuere, vel fermentum nunc dapibus. Pellentesque sit amet elit vel nulla facilisis consequat. Curabitur vitae metus ac nulla posuere tincidunt. In sed diam et quam scelerisque ullamcorper. Etiam id justo a lorem feugiat euismod. Suspendisse ac malesuada libero.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1934,
    '2024-06-10',
    '2024-12-21'
);

INSERT INTO course_creators (course_id, creator) VALUES (3, 'Jane Patel');

INSERT INTO tag_courses (course_id, tag_id) VALUES (3, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (3, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (3, 8);


-- Course ID: 4
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Corporate Finance Fundamentals: Decision-Making & Valuation',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tristique nulla at diam efficitur porta. Nam nec ligula nec metus tristique laoreet. Donec sodales augue nec sem convallis tristique. Praesent suscipit velit in dolor convallis, id lacinia magna hendrerit. Integer fringilla erat et dolor vehicula, nec luctus felis suscipit.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    156,
    '2024-08-03',
    '2024-11-09'
);

INSERT INTO course_creators (course_id, creator) VALUES (4, 'Emily Zhang');
INSERT INTO course_creators (course_id, creator) VALUES (4, 'Marcus Green');

INSERT INTO tag_courses (course_id, tag_id) VALUES (4, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (4, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (4, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (4, 17);
INSERT INTO tag_courses (course_id, tag_id) VALUES (4, 9);


-- Course ID: 5
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Reporting & Analysis: Accounting for CFA® Candidates',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sed lacus et metus facilisis scelerisque nec eget velit. Proin lobortis sapien et nisl tincidunt dignissim. Mauris fringilla nisi non leo vestibulum, non laoreet metus porta. Sed malesuada lorem nec orci laoreet, ut sollicitudin justo fermentum.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    712,
    '2024-05-02',
    '2025-01-11'
);

INSERT INTO course_creators (course_id, creator) VALUES (5, 'Carlos Rivera');

INSERT INTO tag_courses (course_id, tag_id) VALUES (5, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (5, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (5, 16);
INSERT INTO tag_courses (course_id, tag_id) VALUES (5, 19);


-- Course ID: 6
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Ethics & Professional Standards: CFA® Code in Action',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae. Sed vehicula nulla eu ligula dapibus gravida. Vivamus nec orci nulla. Aenean porta est non purus posuere blandit. Cras a dolor a augue finibus commodo.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1340,
    '2024-09-18',
    '2024-10-25'
);

INSERT INTO course_creators (course_id, creator) VALUES (6, 'John Doe');

INSERT INTO tag_courses (course_id, tag_id) VALUES (6, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (6, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (6, 20);


-- INSERT INTO LEARNING PATH 1
INSERT INTO learning_path_courses (learning_path_id, course_id) VALUES (1, 1);
INSERT INTO learning_path_courses (learning_path_id, course_id) VALUES (1, 2);
INSERT INTO learning_path_courses (learning_path_id, course_id) VALUES (1, 3);
INSERT INTO learning_path_courses (learning_path_id, course_id) VALUES (1, 4);
INSERT INTO learning_path_courses (learning_path_id, course_id) VALUES (1, 5);
INSERT INTO learning_path_courses (learning_path_id, course_id) VALUES (1, 6);

-- Course ID: 7
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Behavioral Finance: Psychology in Financial Decisions',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi placerat finibus justo, at tincidunt purus egestas a. Nulla facilisi. Aliquam erat volutpat. Phasellus dapibus ligula in magna porta, at imperdiet est tincidunt. Vivamus eget tellus non justo dictum pretium.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    496,
    '2024-03-28',
    '2024-10-15'
);

INSERT INTO course_creators (course_id, creator) VALUES (7, 'Sophia Lee');
INSERT INTO course_creators (course_id, creator) VALUES (7, 'Michael Chen');

INSERT INTO tag_courses (course_id, tag_id) VALUES (7, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (7, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (7, 18);
INSERT INTO tag_courses (course_id, tag_id) VALUES (7, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (7, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (7, 9);


-- Course ID: 8
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Fixed Income Essentials: Bonds and Interest Rates',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean non tincidunt elit. Sed ac nibh at nulla luctus tincidunt. Curabitur sed lacus eget felis porttitor viverra. Suspendisse convallis nulla ac mi faucibus, at elementum libero egestas.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1079,
    '2024-06-30',
    '2024-12-03'
);

INSERT INTO course_creators (course_id, creator) VALUES (8, 'Noah Brown');

INSERT INTO tag_courses (course_id, tag_id) VALUES (8, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (8, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (8, 13);


-- Course ID: 9
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Ethics in Finance: Principles and Practices',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam facilisis ex et velit accumsan tempor. Sed tincidunt sapien a felis fermentum malesuada. Morbi id lacus in orci tincidunt fermentum. Vestibulum eget nisl velit. Curabitur vel magna sed elit faucibus euismod. Donec vitae lorem in justo eleifend sollicitudin.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    205,
    '2024-07-02',
    '2024-11-05'
);

INSERT INTO course_creators (course_id, creator) VALUES (9, 'Emma Wilson');
INSERT INTO course_creators (course_id, creator) VALUES (9, 'Liam Scott');

INSERT INTO tag_courses (course_id, tag_id) VALUES (9, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (9, 14);


-- Course ID: 10
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Quantitative Methods for Finance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ut dignissim lorem, vel volutpat libero. Duis sit amet mattis lacus. In hac habitasse platea dictumst. Nunc dictum nulla eu nulla sodales, at cursus elit hendrerit. Vivamus quis bibendum sapien.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1653,
    '2024-05-14',
    '2025-02-01'
);

INSERT INTO course_creators (course_id, creator) VALUES (10, 'David Miller');

INSERT INTO tag_courses (course_id, tag_id) VALUES (10, 17);
INSERT INTO tag_courses (course_id, tag_id) VALUES (10, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (10, 19);
INSERT INTO tag_courses (course_id, tag_id) VALUES (10, 4);



-- Course ID: 11
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Personal Finance 101: Budgeting, Saving & Investing',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur fermentum felis at sapien efficitur blandit. Pellentesque nec suscipit tortor, eget dapibus nisi. Donec tempor lorem in turpis ornare, non malesuada elit porttitor. Aenean sed magna sed orci lacinia sodales.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1823,
    '2024-10-01',
    '2025-01-15'
);

INSERT INTO course_creators (course_id, creator) VALUES (11, 'Olivia Davis');

INSERT INTO tag_courses (course_id, tag_id) VALUES (11, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (11, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (11, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (11, 20);


-- Course ID: 12
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Portfolio Management Strategies: Risk & Return',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus et libero vel lectus rutrum varius. Pellentesque ac dignissim ipsum. Maecenas tincidunt, augue ac tempus porttitor, tellus augue finibus eros, eget gravida sapien sem in massa.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    893,
    '2024-04-07',
    '2024-11-22'
);

INSERT INTO course_creators (course_id, creator) VALUES (12, 'Henry Adams');
INSERT INTO course_creators (course_id, creator) VALUES (12, 'Isabella Moore');

INSERT INTO tag_courses (course_id, tag_id) VALUES (12, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (12, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (12, 6);

-- Course ID: 13
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Risk Management Fundamentals: Identifying and Mitigating Risk',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent dictum libero eget lacus tincidunt, nec suscipit magna blandit. Nulla a tincidunt nunc. Integer eget purus sed lacus varius egestas. Sed porta velit a commodo cursus. Donec et enim at orci tincidunt facilisis. Aenean vitae nulla vitae sapien imperdiet tincidunt.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    624,
    '2024-05-25',
    '2025-01-08'
);

INSERT INTO course_creators (course_id, creator) VALUES (13, 'Natalie Brooks');

INSERT INTO tag_courses (course_id, tag_id) VALUES (13, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (13, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (13, 17);


-- Course ID: 14
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Macroeconomics for Finance Professionals II',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras convallis, justo in lacinia fermentum, felis elit sollicitudin orci, sed sollicitudin lacus augue a sem. Donec mollis posuere sem, vel tempus lorem tincidunt non. Etiam eu sagittis odio. Fusce nec orci ut lorem efficitur aliquam a ut metus.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1480,
    '2024-02-19',
    '2024-10-11'
);

INSERT INTO course_creators (course_id, creator) VALUES (14, 'Benjamin Turner');
INSERT INTO course_creators (course_id, creator) VALUES (14, 'Laura King');

INSERT INTO tag_courses (course_id, tag_id) VALUES (14, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (14, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (14, 18);
INSERT INTO tag_courses (course_id, tag_id) VALUES (14, 7);


-- Course ID: 15
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Capital Budgeting Techniques: NPV, IRR, and More',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et lacus tincidunt, gravida lorem et, placerat massa. Integer at magna at erat imperdiet fermentum. Morbi nec libero feugiat, rutrum justo sed, tincidunt purus. Suspendisse ullamcorper nulla nec risus posuere, at posuere purus porttitor.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    137,
    '2024-07-05',
    '2025-03-19'
);

INSERT INTO course_creators (course_id, creator) VALUES (15, 'Grace Hall');

INSERT INTO tag_courses (course_id, tag_id) VALUES (15, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (15, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (15, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (15, 20);
INSERT INTO tag_courses (course_id, tag_id) VALUES (15, 2);


-- Course ID: 16
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Modeling in Excel: From Basics to Projections',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce dapibus ante et libero malesuada, nec eleifend nulla imperdiet. Vivamus bibendum, sapien nec elementum finibus, elit nulla euismod augue, vitae ullamcorper leo libero at ante. Sed ullamcorper enim et purus aliquam, nec pretium magna scelerisque.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1947,
    '2024-06-15',
    '2025-02-20'
);

INSERT INTO course_creators (course_id, creator) VALUES (16, 'Ella Watson');
INSERT INTO course_creators (course_id, creator) VALUES (16, 'Samuel Wright');

INSERT INTO tag_courses (course_id, tag_id) VALUES (16, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (16, 16);
INSERT INTO tag_courses (course_id, tag_id) VALUES (16, 6);


-- Course ID: 17
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Fintech Innovations: Disrupting Traditional Finance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In condimentum nulla non lorem suscipit, vel hendrerit justo feugiat. Nullam posuere turpis non vehicula imperdiet.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    832,
    '2024-04-29',
    '2024-12-18'
);

INSERT INTO course_creators (course_id, creator) VALUES (17, 'Ryan Cooper');

INSERT INTO tag_courses (course_id, tag_id) VALUES (17, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (17, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (17, 19);
INSERT INTO tag_courses (course_id, tag_id) VALUES (17, 14);

-- Course ID: 18
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Sustainable Investing: ESG Principles in Practice',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam at erat nec nisi volutpat rutrum. Integer vitae ex sed nunc sagittis fermentum. Duis et risus non justo convallis blandit. Quisque nec nunc in sem faucibus sodales. Curabitur ultricies nisi ac velit dictum, eget congue odio sodales.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    945,
    '2024-03-12',
    '2024-10-07'
);

INSERT INTO course_creators (course_id, creator) VALUES (18, 'Zoe Mitchell');

INSERT INTO tag_courses (course_id, tag_id) VALUES (18, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (18, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (18, 1);


-- Course ID: 19
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Mergers & Acquisitions: Deal Structuring and Analysis',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit orci vel ipsum euismod, in tempor tortor euismod. Sed id massa sit amet tortor egestas cursus a nec justo. Duis vel velit eu metus dignissim consequat nec sed nulla.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    388,
    '2024-02-10',
    '2024-11-13'
);

INSERT INTO course_creators (course_id, creator) VALUES (19, 'Luke Morgan');
INSERT INTO course_creators (course_id, creator) VALUES (19, 'Anna Fisher');

INSERT INTO tag_courses (course_id, tag_id) VALUES (19, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (19, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (19, 18);
INSERT INTO tag_courses (course_id, tag_id) VALUES (19, 6);

-- Course ID: 20
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'International Finance: Exchange Rates and Global Markets',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras viverra felis vitae luctus tincidunt. Nulla facilisi. Pellentesque porttitor, sapien at rutrum tincidunt, lectus sem dignissim ligula, in varius lectus felis ut justo. Etiam ut diam sed sem aliquam sagittis.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1586,
    '2024-08-28',
    '2025-02-17'
);

INSERT INTO course_creators (course_id, creator) VALUES (20, 'Chloe Bennett');

INSERT INTO tag_courses (course_id, tag_id) VALUES (20, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (20, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (20, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (20, 17);

-- Course ID: 21
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Statement Analysis: Reading Between the Lines',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean tincidunt risus vel augue mattis, in laoreet lacus varius. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Nulla sed lorem convallis, convallis sapien nec, convallis nulla.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    783,
    '2024-07-12',
    '2025-02-10'
);

INSERT INTO course_creators (course_id, creator) VALUES (21, 'Jasmine Parker');

INSERT INTO tag_courses (course_id, tag_id) VALUES (21, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (21, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (21, 6);


-- Course ID: 22
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Advanced Excel for Finance: Pivot Tables to Forecasting',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tincidunt velit sit amet magna placerat, non condimentum ligula fringilla. Duis eget tellus at odio viverra sodales. Aenean id magna ac urna posuere posuere vel et lorem.',
    'ADVANCED',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1192,
    '2024-05-07',
    '2024-12-16'
);

INSERT INTO course_creators (course_id, creator) VALUES (22, 'Trevor Hayes');
INSERT INTO course_creators (course_id, creator) VALUES (22, 'Mia Sanders');

INSERT INTO tag_courses (course_id, tag_id) VALUES (22, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (22, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (22, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (22, 17);


-- Course ID: 23
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Intro to Financial Planning: Building a Strong Foundation',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla fringilla, arcu in aliquam dignissim, ligula neque convallis sapien, ac tincidunt nisi felis sed augue. Curabitur eget justo ac augue pharetra sagittis.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    321,
    '2024-06-20',
    '2025-01-14'
);

INSERT INTO course_creators (course_id, creator) VALUES (23, 'Sophie Kim');

INSERT INTO tag_courses (course_id, tag_id) VALUES (23, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (23, 16);
INSERT INTO tag_courses (course_id, tag_id) VALUES (23, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (23, 18);


-- Course ID: 24
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Real Estate Finance: Investment and Valuation',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ornare nisi at elit lacinia, sed sollicitudin lorem congue. Nam eget feugiat nulla, ac volutpat lectus. Pellentesque iaculis augue ut nibh lacinia convallis.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1109,
    '2024-04-11',
    '2024-11-03'
);

INSERT INTO course_creators (course_id, creator) VALUES (24, 'Harper Lewis');

INSERT INTO tag_courses (course_id, tag_id) VALUES (24, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (24, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (24, 20);


-- Course ID: 25
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Accounting Principles: Assets, Liabilities, and Equity',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur vehicula fermentum magna, non blandit sem finibus nec. Quisque tincidunt dolor et luctus vehicula. Vivamus vestibulum, purus at tempor dignissim, justo urna luctus ligula, ut pulvinar ligula velit ac nisl.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    607,
    '2024-01-30',
    '2024-10-12'
);

INSERT INTO course_creators (course_id, creator) VALUES (25, 'Gabriel Evans');
INSERT INTO course_creators (course_id, creator) VALUES (25, 'Alyssa Reed');

INSERT INTO tag_courses (course_id, tag_id) VALUES (25, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (25, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (25, 19);


-- Course ID: 26
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Wealth Management Basics: Growing and Protecting Assets',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi feugiat nibh nec nulla luctus, at vestibulum metus pulvinar. Nam feugiat dignissim sapien, at lacinia augue hendrerit sit amet. Sed vestibulum erat nec sem dignissim dignissim.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1599,
    '2024-08-16',
    '2025-02-22'
);

INSERT INTO course_creators (course_id, creator) VALUES (26, 'Nora Blake');

INSERT INTO tag_courses (course_id, tag_id) VALUES (26, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (26, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (26, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (26, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (26, 20);


-- Course ID: 27
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Islamic Finance: Principles and Practices',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae sem ut sem cursus rutrum. Cras quis erat sed justo facilisis vestibulum. Aliquam erat volutpat. Donec in lectus sed sem fermentum egestas.',
    'ADVANCED',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    452,
    '2024-03-05',
    '2024-11-27'
);

INSERT INTO course_creators (course_id, creator) VALUES (27, 'Yusuf Khan');

INSERT INTO tag_courses (course_id, tag_id) VALUES (27, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (27, 17);
INSERT INTO tag_courses (course_id, tag_id) VALUES (27, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (27, 10);


-- Course ID: 28
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Treasury Management: Liquidity, Cash, and Risk',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec ligula nec purus hendrerit rutrum. Sed nec leo tincidunt, sodales erat in, vulputate nunc. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1388,
    '2024-07-29',
    '2025-03-04'
);

INSERT INTO course_creators (course_id, creator) VALUES (28, 'Logan Price');
INSERT INTO course_creators (course_id, creator) VALUES (28, 'Isla Simmons');

INSERT INTO tag_courses (course_id, tag_id) VALUES (28, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (28, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (28, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (28, 16);

-- Course ID: 29
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Blockchain in Finance: Decentralized Future Explained',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sed urna eget nulla placerat vestibulum. Duis rutrum lorem a orci cursus, nec dapibus magna vulputate. Pellentesque sit amet sapien nec arcu tincidunt fermentum at sed libero.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1106,
    '2024-06-09',
    '2025-01-25'
);

INSERT INTO course_creators (course_id, creator) VALUES (29, 'Dylan Foster');

INSERT INTO tag_courses (course_id, tag_id) VALUES (29, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (29, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (29, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (29, 20);


-- Course ID: 30
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Intro to Insurance and Risk Transfer',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque malesuada, turpis sed tempus convallis, nisl nulla tincidunt enim, sed pretium neque lorem a nibh. Aliquam in lorem in leo convallis fermentum sed a orci.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    223,
    '2024-02-25',
    '2024-10-02'
);

INSERT INTO course_creators (course_id, creator) VALUES (30, 'Olivia James');
INSERT INTO course_creators (course_id, creator) VALUES (30, 'Ethan Walsh');

INSERT INTO tag_courses (course_id, tag_id) VALUES (30, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (30, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (30, 18);


-- Course ID: 31
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'CFO Crash Course: Strategic Financial Leadership',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec nisi feugiat, cursus ligula nec, tristique turpis. Donec porttitor, justo in bibendum tempor, augue nunc congue odio, id feugiat turpis arcu sit amet libero.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1744,
    '2024-08-05',
    '2025-01-31'
);

INSERT INTO course_creators (course_id, creator) VALUES (31, 'Chase Hamilton');

INSERT INTO tag_courses (course_id, tag_id) VALUES (31, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (31, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (31, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (31, 15);


-- Course ID: 32
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Intro to Budgeting: Take Control of Your Money',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras non metus nec diam suscipit vestibulum. Vestibulum mattis leo a nulla luctus finibus. Integer ultricies, ex vel gravida fringilla, arcu magna condimentum ligula.',
    'BEGINNER',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1432,
    '2024-01-12',
    '2024-12-20'
);

INSERT INTO course_creators (course_id, creator) VALUES (32, 'Rachel Nguyen');

INSERT INTO tag_courses (course_id, tag_id) VALUES (32, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (32, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (32, 14);


-- Course ID: 33
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Private Equity Essentials: Deal Flow to Exit',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam convallis turpis quis nunc tincidunt, nec cursus justo congue. Integer fringilla imperdiet nunc, et facilisis lorem cursus eget.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    861,
    '2024-04-21',
    '2025-02-12'
);

INSERT INTO course_creators (course_id, creator) VALUES (33, 'Lara Jensen');

INSERT INTO tag_courses (course_id, tag_id) VALUES (33, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (33, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (33, 16);
INSERT INTO tag_courses (course_id, tag_id) VALUES (33, 19);
INSERT INTO tag_courses (course_id, tag_id) VALUES (33, 20);


-- Course ID: 34
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Investment Banking Overview: Functions and Services',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla posuere, lacus eget sodales commodo, nisl augue cursus nulla, vel bibendum nulla lorem at nisl. Pellentesque habitant morbi tristique senectus et netus.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    537,
    '2024-03-15',
    '2024-11-01'
);

INSERT INTO course_creators (course_id, creator) VALUES (34, 'Victor Ellis');
INSERT INTO course_creators (course_id, creator) VALUES (34, 'Carmen Rivera');

INSERT INTO tag_courses (course_id, tag_id) VALUES (34, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (34, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (34, 17);


-- Course ID: 35
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financing Startups: Angel Investors to IPOs',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce cursus magna nec dolor iaculis laoreet. Ut sollicitudin, nisl id sodales tincidunt, tortor libero dapibus mi, id varius velit tellus sed nisl.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1291,
    '2024-05-16',
    '2024-12-27'
);

INSERT INTO course_creators (course_id, creator) VALUES (35, 'Aiden Rivera');

INSERT INTO tag_courses (course_id, tag_id) VALUES (35, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (35, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (35, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (35, 18);


-- Course ID: 36
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Navigating Financial Regulations: Compliance 101',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin convallis sem at dolor ullamcorper, a pulvinar nisi gravida. Nulla ac ante at arcu accumsan sagittis in at ante. Mauris nec justo vitae augue porttitor scelerisque.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1017,
    '2024-06-01',
    '2025-01-10'
);

INSERT INTO course_creators (course_id, creator) VALUES (36, 'Jared Clarke');
INSERT INTO course_creators (course_id, creator) VALUES (36, 'Ellie Morgan');

INSERT INTO tag_courses (course_id, tag_id) VALUES (36, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (36, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (36, 13);

-- Course ID: 37
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Behavioral Finance: Psychology of Investing',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum ac orci eget turpis dignissim faucibus. Proin scelerisque arcu non orci vestibulum, nec cursus sem accumsan. Pellentesque nec libero eu lorem tristique interdum.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1128,
    '2024-05-02',
    '2024-12-09'
);

INSERT INTO course_creators (course_id, creator) VALUES (37, 'Natalie Woods');

INSERT INTO tag_courses (course_id, tag_id) VALUES (37, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (37, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (37, 17);
INSERT INTO tag_courses (course_id, tag_id) VALUES (37, 19);


-- Course ID: 38
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Derivatives Demystified: Options, Futures, and Swaps',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ut est a odio convallis faucibus. Aenean sit amet lorem rutrum, dictum tellus ut, efficitur turpis. Etiam dignissim congue dui at lobortis.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    784,
    '2024-03-28',
    '2025-02-14'
);

INSERT INTO course_creators (course_id, creator) VALUES (38, 'Max Bennett');
INSERT INTO course_creators (course_id, creator) VALUES (38, 'Leah Harris');

INSERT INTO tag_courses (course_id, tag_id) VALUES (38, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (38, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (38, 4);


-- Course ID: 39
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Corporate Finance: Valuation & Capital Structure',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer id arcu viverra, dictum nisi nec, consequat orci. Curabitur tempus, diam a suscipit suscipit, lectus justo ullamcorper ante, nec congue purus tellus sed velit.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1505,
    '2024-04-14',
    '2025-01-30'
);

INSERT INTO course_creators (course_id, creator) VALUES (39, 'Daniel Chen');

INSERT INTO tag_courses (course_id, tag_id) VALUES (39, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (39, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (39, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (39, 20);


-- Course ID: 40
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Cryptocurrency Fundamentals: Bitcoin to DeFi',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In venenatis viverra erat, vitae vehicula augue malesuada at.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1321,
    '2024-02-06',
    '2024-10-21'
);

INSERT INTO course_creators (course_id, creator) VALUES (40, 'Sophia Patel');

INSERT INTO tag_courses (course_id, tag_id) VALUES (40, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (40, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (40, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (40, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (40, 17);


-- Course ID: 41
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Microeconomics for Finance: Supply, Demand, and Markets',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed id nulla in libero luctus rhoncus. Proin in lorem blandit, placerat sem sed, porttitor felis. Integer sed magna in justo blandit scelerisque.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    901,
    '2024-07-19',
    '2025-03-01'
);

INSERT INTO course_creators (course_id, creator) VALUES (41, 'Colin Rivera');
INSERT INTO course_creators (course_id, creator) VALUES (41, 'Emily Watts');

INSERT INTO tag_courses (course_id, tag_id) VALUES (41, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (41, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (41, 11);


-- Course ID: 42
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Ethics in Finance: Standards and Practice',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam porttitor sapien nec facilisis convallis. In eu lorem ac turpis lacinia sodales. Nulla id gravida neque. Pellentesque finibus volutpat dignissim.',
    'BEGINNER',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    348,
    '2024-03-10',
    '2024-11-14'
);

INSERT INTO course_creators (course_id, creator) VALUES (42, 'Grace Monroe');

INSERT INTO tag_courses (course_id, tag_id) VALUES (42, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (42, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (42, 16);
INSERT INTO tag_courses (course_id, tag_id) VALUES (42, 18);


-- Course ID: 43
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Cash Flow Forecasting: Planning Ahead with Confidence',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus porttitor ligula et felis gravida, sit amet gravida tortor egestas. Sed pulvinar ex vel dui tincidunt tristique. Integer vitae odio ut sapien bibendum posuere.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1697,
    '2024-06-26',
    '2025-01-28'
);

INSERT INTO course_creators (course_id, creator) VALUES (43, 'Mason Clark');

INSERT INTO tag_courses (course_id, tag_id) VALUES (43, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (43, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (43, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (43, 20);


-- Course ID: 44
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Intro to Taxation: Personal and Corporate',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed a magna vel erat commodo placerat. Nulla ac tincidunt risus, ac lacinia enim. Donec tincidunt, lorem nec fermentum sodales, enim velit porta justo.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1345,
    '2024-01-23',
    '2024-10-30'
);

INSERT INTO course_creators (course_id, creator) VALUES (44, 'Tyler Brooks');
INSERT INTO course_creators (course_id, creator) VALUES (44, 'Julia Stephens');

INSERT INTO tag_courses (course_id, tag_id) VALUES (44, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (44, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (44, 19);

-- Course ID: 45
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Mergers & Acquisitions: Strategy, Deal, Integration',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque habitant morbi tristique senectus et netus. Integer tincidunt, nunc nec commodo varius, enim metus convallis eros, nec facilisis mi libero at lacus.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1361,
    '2024-05-18',
    '2025-03-03'
);

INSERT INTO course_creators (course_id, creator) VALUES (45, 'Samantha Lin');

INSERT INTO tag_courses (course_id, tag_id) VALUES (45, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (45, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (45, 16);
INSERT INTO tag_courses (course_id, tag_id) VALUES (45, 20);


-- Course ID: 46
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Understanding Bonds: From Yield to Maturity',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas pretium urna in risus convallis, et pretium lorem pharetra. Nam sit amet nunc tellus. Cras nec convallis turpis.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1007,
    '2024-06-03',
    '2025-01-17'
);

INSERT INTO course_creators (course_id, creator) VALUES (46, 'Riley Thornton');

INSERT INTO tag_courses (course_id, tag_id) VALUES (46, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (46, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (46, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (46, 18);


-- Course ID: 47
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Modeling: From Excel to Valuation',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sed feugiat leo. Curabitur viverra imperdiet felis, non egestas risus vehicula sit amet. Sed tristique, nulla sit amet ultrices convallis.',
    'ADVANCED',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1894,
    '2024-04-25',
    '2025-02-13'
);

INSERT INTO course_creators (course_id, creator) VALUES (47, 'Liam Foster');
INSERT INTO course_creators (course_id, creator) VALUES (47, 'Anna Stone');

INSERT INTO tag_courses (course_id, tag_id) VALUES (47, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (47, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (47, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (47, 19);
INSERT INTO tag_courses (course_id, tag_id) VALUES (47, 3);


-- Course ID: 48
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Finance for Non-Financial Professionals',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi in feugiat quam. Fusce eu turpis malesuada, blandit purus ac, hendrerit neque. Cras non accumsan tellus, at dapibus leo.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    453,
    '2024-01-05',
    '2024-10-11'
);

INSERT INTO course_creators (course_id, creator) VALUES (48, 'Alexis Drew');

INSERT INTO tag_courses (course_id, tag_id) VALUES (48, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (48, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (48, 17);


-- Course ID: 49
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Portfolio Theory: Diversification and Risk',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean laoreet arcu sed ipsum porttitor, eget efficitur leo gravida. Sed gravida felis sit amet odio eleifend, nec tincidunt justo congue.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    988,
    '2024-02-12',
    '2024-11-09'
);

INSERT INTO course_creators (course_id, creator) VALUES (49, 'Owen Bryant');
INSERT INTO course_creators (course_id, creator) VALUES (49, 'Naomi Pierce');

INSERT INTO tag_courses (course_id, tag_id) VALUES (49, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (49, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (49, 18);
INSERT INTO tag_courses (course_id, tag_id) VALUES (49, 20);


-- Course ID: 50
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'International Finance: Currency & Global Markets',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum finibus, libero sed mattis sagittis, elit justo fermentum justo, nec imperdiet tortor lorem ut mi.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1725,
    '2024-03-02',
    '2025-01-11'
);

INSERT INTO course_creators (course_id, creator) VALUES (50, 'Zara Mitchell');

INSERT INTO tag_courses (course_id, tag_id) VALUES (50, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (50, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (50, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (50, 5);


-- Course ID: 51
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Finance Interview Prep: Ace the Questions',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae urna nec mi volutpat imperdiet. Aliquam volutpat justo at ex mattis, vel dignissim lorem malesuada. In at porttitor sem.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    329,
    '2024-07-06',
    '2025-02-04'
);

INSERT INTO course_creators (course_id, creator) VALUES (51, 'Nina Alvarez');

INSERT INTO tag_courses (course_id, tag_id) VALUES (51, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (51, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (51, 13);


-- Course ID: 52
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Data Analysis for Finance: From Raw Data to Insight',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit laoreet mi nec congue. Nulla facilisi. Aenean nec magna ut tellus imperdiet dictum a nec massa.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1466,
    '2024-06-18',
    '2025-03-06'
);

INSERT INTO course_creators (course_id, creator) VALUES (52, 'Isabelle Roy');
INSERT INTO course_creators (course_id, creator) VALUES (52, 'Julian Morris');

INSERT INTO tag_courses (course_id, tag_id) VALUES (52, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (52, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (52, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (52, 11);

-- Course ID: 53
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Accounting Fundamentals: Balance Sheets & Beyond',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis tincidunt lorem, nec rutrum velit malesuada et. Integer sed purus ut risus cursus sagittis. Nulla imperdiet purus at blandit finibus.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    763,
    '2024-02-28',
    '2024-10-04'
);

INSERT INTO course_creators (course_id, creator) VALUES (53, 'Tara Blake');

INSERT INTO tag_courses (course_id, tag_id) VALUES (53, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (53, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (53, 17);


-- Course ID: 54
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Intro to ESG Finance: Environment, Social, Governance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed a luctus turpis. Donec nec dignissim nisl. Etiam ultrices lacus ac erat ullamcorper, eget ullamcorper felis sodales.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1209,
    '2024-03-18',
    '2025-01-05'
);

INSERT INTO course_creators (course_id, creator) VALUES (54, 'Logan Barrett');
INSERT INTO course_creators (course_id, creator) VALUES (54, 'Mei Tan');

INSERT INTO tag_courses (course_id, tag_id) VALUES (54, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (54, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (54, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (54, 18);


-- Course ID: 55
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Banking Basics: From Retail to Investment',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse nec quam at lacus posuere vestibulum. Quisque nec neque quis magna hendrerit laoreet. Sed quis felis ligula.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    436,
    '2024-01-14',
    '2024-11-22'
);

INSERT INTO course_creators (course_id, creator) VALUES (55, 'Hannah Carter');

INSERT INTO tag_courses (course_id, tag_id) VALUES (55, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (55, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (55, 20);


-- Course ID: 56
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Risk Management: Tools and Techniques',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer lacinia mi non quam lacinia, ac volutpat sem euismod. Aliquam erat volutpat. Nullam a finibus justo.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1587,
    '2024-04-11',
    '2025-02-09'
);

INSERT INTO course_creators (course_id, creator) VALUES (56, 'Avery Miles');

INSERT INTO tag_courses (course_id, tag_id) VALUES (56, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (56, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (56, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (56, 19);


-- Course ID: 57
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Budgeting for Freelancers: Manage Irregular Income',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris gravida ipsum vel fermentum pulvinar. Donec nec purus in enim dapibus malesuada. Cras condimentum libero sit amet nulla lacinia.',
    'BEGINNER',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    674,
    '2024-05-07',
    '2024-12-15'
);

INSERT INTO course_creators (course_id, creator) VALUES (57, 'Jade Reynolds');

INSERT INTO tag_courses (course_id, tag_id) VALUES (57, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (57, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (57, 15);


-- Course ID: 58
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Quantitative Finance: Algorithms & Trading Strategies',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tincidunt enim ac efficitur accumsan. Curabitur vulputate laoreet diam, a vestibulum risus mollis eget.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1982,
    '2024-06-24',
    '2025-03-11'
);

INSERT INTO course_creators (course_id, creator) VALUES (58, 'Kai Navarro');
INSERT INTO course_creators (course_id, creator) VALUES (58, 'Morgan Tate');

INSERT INTO tag_courses (course_id, tag_id) VALUES (58, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (58, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (58, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (58, 17);
INSERT INTO tag_courses (course_id, tag_id) VALUES (58, 20);


-- Course ID: 59
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Real Estate Finance: Analyze & Invest Smart',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ac magna eget erat efficitur fringilla. Sed tincidunt, lacus vitae egestas placerat, orci augue bibendum ex, ac cursus nisi erat id dolor.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1119,
    '2024-02-17',
    '2025-01-04'
);

INSERT INTO course_creators (course_id, creator) VALUES (59, 'Caleb Ortiz');

INSERT INTO tag_courses (course_id, tag_id) VALUES (59, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (59, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (59, 16);


-- Course ID: 60
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Fintech Fundamentals: Innovations in Finance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur hendrerit lorem et posuere convallis. Sed et est ac sem vulputate rutrum. Integer feugiat massa et vehicula malesuada.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1803,
    '2024-03-30',
    '2025-01-27'
);

INSERT INTO course_creators (course_id, creator) VALUES (60, 'Elena Romano');
INSERT INTO course_creators (course_id, creator) VALUES (60, 'Malik Dawson');

INSERT INTO tag_courses (course_id, tag_id) VALUES (60, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (60, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (60, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (60, 18);

-- Course ID: 61
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Equity Research Essentials: Company & Sector Analysis',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ac congue magna. Aenean dapibus, purus at scelerisque vulputate, lacus sem pulvinar velit, at sagittis nulla ipsum nec neque.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1742,
    '2024-04-07',
    '2025-02-23'
);

INSERT INTO course_creators (course_id, creator) VALUES (61, 'Jasper Kane');
INSERT INTO course_creators (course_id, creator) VALUES (61, 'Tessa Shaw');

INSERT INTO tag_courses (course_id, tag_id) VALUES (61, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (61, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (61, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (61, 18);


-- Course ID: 62
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Fundamentals of Financial Planning & Analysis (FP&A)',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Suspendisse dignissim lacus eu velit tempus, ut ornare libero malesuada. Donec eget orci nec metus pretium pharetra.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    936,
    '2024-05-14',
    '2025-01-09'
);

INSERT INTO course_creators (course_id, creator) VALUES (62, 'Noah Sinclair');

INSERT INTO tag_courses (course_id, tag_id) VALUES (62, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (62, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (62, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (62, 20);


-- Course ID: 63
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Venture Capital 101: Startups & Term Sheets',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi ac sem eget enim congue faucibus. Vestibulum vel neque in neque facilisis aliquam at non justo.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    582,
    '2024-01-30',
    '2024-12-08'
);

INSERT INTO course_creators (course_id, creator) VALUES (63, 'Jordan Wells');

INSERT INTO tag_courses (course_id, tag_id) VALUES (63, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (63, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (63, 17);
INSERT INTO tag_courses (course_id, tag_id) VALUES (63, 19);


-- Course ID: 64
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Advanced Excel for Finance Professionals',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis orci a magna sollicitudin, in fermentum arcu sollicitudin. Nunc mattis vel velit vitae feugiat.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1980,
    '2024-06-10',
    '2025-02-27'
);

INSERT INTO course_creators (course_id, creator) VALUES (64, 'Maya Griffith');
INSERT INTO course_creators (course_id, creator) VALUES (64, 'Henry Lowe');

INSERT INTO tag_courses (course_id, tag_id) VALUES (64, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (64, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (64, 12);


-- Course ID: 65
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Wealth Management: Personal Portfolio Strategies',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras tempor nibh in nisi elementum, id ultrices sem scelerisque. Integer fringilla libero ac fermentum cursus.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1506,
    '2024-03-20',
    '2025-01-02'
);

INSERT INTO course_creators (course_id, creator) VALUES (65, 'Isla Brennan');

INSERT INTO tag_courses (course_id, tag_id) VALUES (65, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (65, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (65, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (65, 16);


-- Course ID: 66
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Intro to Microfinance: Empowering Small Business',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris dapibus leo at mauris sodales, at scelerisque erat fermentum. Duis ut imperdiet augue, non scelerisque orci.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    421,
    '2024-02-09',
    '2024-10-26'
);

INSERT INTO course_creators (course_id, creator) VALUES (66, 'Eva Kramer');

INSERT INTO tag_courses (course_id, tag_id) VALUES (66, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (66, 18);
INSERT INTO tag_courses (course_id, tag_id) VALUES (66, 20);


-- Course ID: 67
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Due Diligence: M&A and Investment Decisions',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque blandit justo in turpis efficitur, non efficitur nunc viverra. Sed in purus tincidunt, commodo augue ac, suscipit mauris.',
    'ADVANCED',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1693,
    '2024-01-18',
    '2024-12-01'
);

INSERT INTO course_creators (course_id, creator) VALUES (67, 'Brandon Liu');

INSERT INTO tag_courses (course_id, tag_id) VALUES (67, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (67, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (67, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (67, 19);


-- Course ID: 68
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Introduction to Actuarial Science',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur nec ante eu est gravida facilisis. Integer mollis pretium lorem, nec tristique tortor malesuada at.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1048,
    '2024-04-28',
    '2025-01-26'
);

INSERT INTO course_creators (course_id, creator) VALUES (68, 'Lena McCoy');

INSERT INTO tag_courses (course_id, tag_id) VALUES (68, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (68, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (68, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (68, 14);

-- Course ID: 69
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Corporate Finance Explained: Capital & Valuation',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi et rhoncus purus. Phasellus tristique, nulla in finibus fringilla, sapien justo fermentum velit, et euismod ligula elit ut turpis.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1891,
    '2024-03-09',
    '2025-02-15'
);

INSERT INTO course_creators (course_id, creator) VALUES (69, 'Miles Becker');
INSERT INTO course_creators (course_id, creator) VALUES (69, 'Sophia Wells');

INSERT INTO tag_courses (course_id, tag_id) VALUES (69, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (69, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (69, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (69, 17);
INSERT INTO tag_courses (course_id, tag_id) VALUES (69, 20);


-- Course ID: 70
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Behavioral Finance: Psychology in Markets',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse rutrum lacus nec sagittis rhoncus. Sed lacinia enim sed massa fringilla, vel dignissim felis laoreet.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1342,
    '2024-01-22',
    '2025-01-03'
);

INSERT INTO course_creators (course_id, creator) VALUES (70, 'Elijah Rhodes');

INSERT INTO tag_courses (course_id, tag_id) VALUES (70, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (70, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (70, 12);


-- Course ID: 71
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Cash Flow Management for Small Business Owners',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed bibendum orci sed risus fringilla, a iaculis tellus vulputate. Aliquam erat volutpat. Cras dignissim, lacus non laoreet dapibus.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    684,
    '2024-05-12',
    '2024-12-20'
);

INSERT INTO course_creators (course_id, creator) VALUES (71, 'Dana Park');

INSERT INTO tag_courses (course_id, tag_id) VALUES (71, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (71, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (71, 18);
INSERT INTO tag_courses (course_id, tag_id) VALUES (71, 19);


-- Course ID: 72
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Insurance and Risk: Concepts for Finance Professionals',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In feugiat, odio non volutpat accumsan, tellus quam fermentum neque, vel feugiat massa tortor a nunc.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1114,
    '2024-02-27',
    '2025-01-22'
);

INSERT INTO course_creators (course_id, creator) VALUES (72, 'Reed Ellis');

INSERT INTO tag_courses (course_id, tag_id) VALUES (72, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (72, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (72, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (72, 20);


-- Course ID: 73
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Intro to Blockchain in Finance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus malesuada, diam eget tincidunt porta, ex orci porttitor orci, a tristique turpis ex non nisi.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    792,
    '2024-06-15',
    '2025-01-30'
);

INSERT INTO course_creators (course_id, creator) VALUES (73, 'Taylor Voss');
INSERT INTO course_creators (course_id, creator) VALUES (73, 'Olivia Hahn');

INSERT INTO tag_courses (course_id, tag_id) VALUES (73, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (73, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (73, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (73, 16);


-- Course ID: 74
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Ethics in Finance: Principles and Practice',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pretium ex sed suscipit sagittis. Integer pharetra ligula vel urna maximus, nec congue nisi sagittis.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    558,
    '2024-03-17',
    '2024-11-14'
);

INSERT INTO course_creators (course_id, creator) VALUES (74, 'Casey Nguyen');

INSERT INTO tag_courses (course_id, tag_id) VALUES (74, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (74, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (74, 13);


-- Course ID: 75
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Commodities & Derivatives: A Practical Guide',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nec libero sed sem suscipit gravida. Nam pretium orci id dolor vestibulum, vitae sollicitudin metus facilisis.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1794,
    '2024-04-22',
    '2025-03-10'
);

INSERT INTO course_creators (course_id, creator) VALUES (75, 'Leo Andrade');

INSERT INTO tag_courses (course_id, tag_id) VALUES (75, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (75, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (75, 17);
INSERT INTO tag_courses (course_id, tag_id) VALUES (75, 18);


-- Course ID: 76
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Macroeconomics for Finance Professionals',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean hendrerit justo sit amet nisi efficitur, eget sagittis erat convallis. Curabitur accumsan sapien in tortor laoreet feugiat.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1250,
    '2024-05-04',
    '2025-01-08'
);

INSERT INTO course_creators (course_id, creator) VALUES (76, 'Veronica Steele');

INSERT INTO tag_courses (course_id, tag_id) VALUES (76, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (76, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (76, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (76, 15);

-- Course ID: 77
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Startup Finance: From Idea to Series A',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras eget fringilla diam. Proin nec mauris in leo efficitur porta. Pellentesque in mauris turpis. Etiam rhoncus ante sed erat fermentum.',
    'BEGINNER',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    812,
    '2024-02-21',
    '2025-01-12'
);

INSERT INTO course_creators (course_id, creator) VALUES (77, 'Natalie Cross');

INSERT INTO tag_courses (course_id, tag_id) VALUES (77, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (77, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (77, 15);


-- Course ID: 78
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Excel Modeling for Investment Analysis',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse posuere efficitur felis at condimentum. Vivamus a mi vitae nisl sollicitudin bibendum nec ut tortor.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1703,
    '2024-01-11',
    '2024-12-06'
);

INSERT INTO course_creators (course_id, creator) VALUES (78, 'Zachary Lin');
INSERT INTO course_creators (course_id, creator) VALUES (78, 'Riley Vaughn');

INSERT INTO tag_courses (course_id, tag_id) VALUES (78, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (78, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (78, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (78, 20);


-- Course ID: 79
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Intro to International Finance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam ut massa dignissim, facilisis sem nec, fermentum mauris. Nam non diam nec tellus tempus finibus.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    942,
    '2024-05-17',
    '2025-02-01'
);

INSERT INTO course_creators (course_id, creator) VALUES (79, 'Aliya Grant');

INSERT INTO tag_courses (course_id, tag_id) VALUES (79, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (79, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (79, 16);
INSERT INTO tag_courses (course_id, tag_id) VALUES (79, 19);


-- Course ID: 80
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Hedge Fund Strategies: Market Neutral & More',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque rutrum rutrum lectus, nec eleifend eros congue a. Curabitur vel nunc felis. Duis malesuada est orci, nec bibendum.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1624,
    '2024-04-26',
    '2025-03-02'
);

INSERT INTO course_creators (course_id, creator) VALUES (80, 'Freddie Yoon');

INSERT INTO tag_courses (course_id, tag_id) VALUES (80, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (80, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (80, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (80, 17);
INSERT INTO tag_courses (course_id, tag_id) VALUES (80, 20);


-- Course ID: 81
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Introduction to Taxation for Finance Professionals',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed at mauris sit amet urna dapibus tempus. Nulla facilisi. Etiam sodales dui vitae ex posuere, id tristique felis vulputate.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    516,
    '2024-03-05',
    '2024-11-29'
);

INSERT INTO course_creators (course_id, creator) VALUES (81, 'Renee Delgado');

INSERT INTO tag_courses (course_id, tag_id) VALUES (81, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (81, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (81, 18);


-- Course ID: 82
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Data Analysis in Finance with Python',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed sit amet nulla id libero.',
    'ADVANCED',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1839,
    '2024-02-13',
    '2025-01-25'
);

INSERT INTO course_creators (course_id, creator) VALUES (82, 'Kieran Dorsey');
INSERT INTO course_creators (course_id, creator) VALUES (82, 'Lila Monroe');

INSERT INTO tag_courses (course_id, tag_id) VALUES (82, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (82, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (82, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (82, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (82, 18);


-- Course ID: 83
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Portfolio Theory: Diversification & Optimization',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vehicula, nunc in rutrum imperdiet, sem quam fringilla ex, non congue ante ante in sem.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1317,
    '2024-01-27',
    '2024-12-09'
);

INSERT INTO course_creators (course_id, creator) VALUES (83, 'Emmett Floyd');

INSERT INTO tag_courses (course_id, tag_id) VALUES (83, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (83, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (83, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (83, 19);


-- Course ID: 84
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Credit Analysis Fundamentals',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean scelerisque augue nec tortor tincidunt, vitae aliquam purus pulvinar. Cras et lectus tellus. Fusce sed arcu sed nunc dignissim.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    998,
    '2024-04-03',
    '2025-01-18'
);

INSERT INTO course_creators (course_id, creator) VALUES (84, 'Marcus Benson');

INSERT INTO tag_courses (course_id, tag_id) VALUES (84, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (84, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (84, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (84, 16);

-- Course ID: 85
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Sustainable Finance: ESG Investing Basics',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer vel nibh nec justo accumsan hendrerit. Vestibulum posuere nulla id posuere laoreet. Nam luctus enim at odio fermentum.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    732,
    '2024-02-11',
    '2025-01-14'
);

INSERT INTO course_creators (course_id, creator) VALUES (85, 'Bianca Shaw');

INSERT INTO tag_courses (course_id, tag_id) VALUES (85, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (85, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (85, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (85, 17);


-- Course ID: 86
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Valuation Techniques: DCF, Multiples & More',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean faucibus eros vel risus ornare, ac tincidunt ex euismod. Mauris ut nunc id erat laoreet feugiat.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1645,
    '2024-03-06',
    '2025-02-18'
);

INSERT INTO course_creators (course_id, creator) VALUES (86, 'Logan Chen');
INSERT INTO course_creators (course_id, creator) VALUES (86, 'Avery Patel');

INSERT INTO tag_courses (course_id, tag_id) VALUES (86, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (86, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (86, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (86, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (86, 15);


-- Course ID: 87
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Forecasting for Strategic Planning',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin at lectus at nunc viverra vehicula. Etiam feugiat efficitur odio, nec convallis magna dictum at.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1273,
    '2024-05-28',
    '2025-01-21'
);

INSERT INTO course_creators (course_id, creator) VALUES (87, 'Lena Armstrong');

INSERT INTO tag_courses (course_id, tag_id) VALUES (87, 4);
INSERT INTO tag_courses (course_id, tag_id) VALUES (87, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (87, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (87, 19);


-- Course ID: 88
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial History & Crises: What We Can Learn',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sed turpis vitae odio hendrerit tempus. Suspendisse potenti. Pellentesque lacinia urna nec lorem blandit, sed sollicitudin magna tincidunt.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    438,
    '2024-04-15',
    '2024-12-12'
);

INSERT INTO course_creators (course_id, creator) VALUES (88, 'Soren Keller');

INSERT INTO tag_courses (course_id, tag_id) VALUES (88, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (88, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (88, 16);


-- Course ID: 89
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Budgeting 101: Managing Personal & Business Finances',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque feugiat arcu vel ipsum varius, eget tincidunt eros fermentum. Nam nec fermentum tellus, nec posuere erat.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    892,
    '2024-01-14',
    '2024-11-30'
);

INSERT INTO course_creators (course_id, creator) VALUES (89, 'Mariah Quinn');
INSERT INTO course_creators (course_id, creator) VALUES (89, 'Felix Boyd');

INSERT INTO tag_courses (course_id, tag_id) VALUES (89, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (89, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (89, 18);


-- Course ID: 90
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Introduction to Quantitative Finance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porta, tortor at dictum laoreet, augue libero vulputate ex, at tristique eros lorem sed enim.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1557,
    '2024-06-05',
    '2025-02-13'
);

INSERT INTO course_creators (course_id, creator) VALUES (90, 'Sasha Meadows');

INSERT INTO tag_courses (course_id, tag_id) VALUES (90, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (90, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (90, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (90, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (90, 20);


-- Course ID: 91
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Real Estate Finance & Investment Analysis',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et mauris dignissim, volutpat erat non, convallis massa. Suspendisse finibus vel turpis ac iaculis.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1389,
    '2024-02-19',
    '2025-01-16'
);

INSERT INTO course_creators (course_id, creator) VALUES (91, 'Kaitlyn Brooks');

INSERT INTO tag_courses (course_id, tag_id) VALUES (91, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (91, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (91, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (91, 17);


-- Course ID: 92
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Ethical Decision-Making in Finance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur a eros non nunc fermentum luctus. Integer posuere risus a velit vehicula, nec iaculis sapien tincidunt.',
    'BEGINNER',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    665,
    '2024-03-13',
    '2024-12-03'
);

INSERT INTO course_creators (course_id, creator) VALUES (92, 'Tristan Webb');

INSERT INTO tag_courses (course_id, tag_id) VALUES (92, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (92, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (92, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (92, 19);

-- Course ID: 93
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Mergers & Acquisitions: Strategy and Modeling',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sed lectus vitae nulla finibus lacinia. Curabitur ut turpis at arcu tincidunt suscipit ut vitae mauris.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1734,
    '2024-04-18',
    '2025-02-24'
);

INSERT INTO course_creators (course_id, creator) VALUES (93, 'Jude Sandoval');

INSERT INTO tag_courses (course_id, tag_id) VALUES (93, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (93, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (93, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (93, 18);


-- Course ID: 94
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Introduction to Budgeting & Cost Control',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam bibendum felis sit amet sapien commodo, eget tincidunt velit eleifend. Praesent ut turpis eu velit fermentum.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    597,
    '2024-03-09',
    '2024-12-18'
);

INSERT INTO course_creators (course_id, creator) VALUES (94, 'Luca Nolan');
INSERT INTO course_creators (course_id, creator) VALUES (94, 'Isabelle Vega');

INSERT INTO tag_courses (course_id, tag_id) VALUES (94, 1);
INSERT INTO tag_courses (course_id, tag_id) VALUES (94, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (94, 14);


-- Course ID: 95
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Fixed Income Securities: Bonds & Beyond',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum sem ac odio dapibus, ut placerat nunc vestibulum. Curabitur egestas dolor eu nulla egestas.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1231,
    '2024-02-25',
    '2025-01-29'
);

INSERT INTO course_creators (course_id, creator) VALUES (95, 'Tobias Reed');

INSERT INTO tag_courses (course_id, tag_id) VALUES (95, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (95, 9);
INSERT INTO tag_courses (course_id, tag_id) VALUES (95, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (95, 17);


-- Course ID: 96
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Finance for Product Managers',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque blandit, erat nec mattis posuere, felis augue fermentum ex, at tempus purus lorem at risus.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    867,
    '2024-01-22',
    '2024-11-10'
);

INSERT INTO course_creators (course_id, creator) VALUES (96, 'Hazel Boone');

INSERT INTO tag_courses (course_id, tag_id) VALUES (96, 5);
INSERT INTO tag_courses (course_id, tag_id) VALUES (96, 10);
INSERT INTO tag_courses (course_id, tag_id) VALUES (96, 12);
INSERT INTO tag_courses (course_id, tag_id) VALUES (96, 16);


-- Course ID: 97
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Leveraged Finance Fundamentals',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin sit amet sem vitae.',
    'ADVANCED',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1479,
    '2024-06-03',
    '2025-03-07'
);

INSERT INTO course_creators (course_id, creator) VALUES (97, 'Calvin Waters');

INSERT INTO tag_courses (course_id, tag_id) VALUES (97, 8);
INSERT INTO tag_courses (course_id, tag_id) VALUES (97, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (97, 18);
INSERT INTO tag_courses (course_id, tag_id) VALUES (97, 20);


-- Course ID: 98
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Intro to AI & Machine Learning in Finance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sed risus quis ipsum porta iaculis. Etiam nec nulla sagittis, feugiat elit sit amet, finibus sapien.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1395,
    '2024-02-12',
    '2025-01-05'
);

INSERT INTO course_creators (course_id, creator) VALUES (98, 'Sienna Holloway');

INSERT INTO tag_courses (course_id, tag_id) VALUES (98, 7);
INSERT INTO tag_courses (course_id, tag_id) VALUES (98, 13);
INSERT INTO tag_courses (course_id, tag_id) VALUES (98, 15);
INSERT INTO tag_courses (course_id, tag_id) VALUES (98, 19);


-- Course ID: 99
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Government & Public Finance Essentials',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse nec sapien hendrerit, imperdiet justo ac, accumsan nulla. Curabitur lacinia massa at purus porttitor consequat.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    624,
    '2024-03-20',
    '2025-01-10'
);

INSERT INTO course_creators (course_id, creator) VALUES (99, 'Ari Levine');

INSERT INTO tag_courses (course_id, tag_id) VALUES (99, 2);
INSERT INTO tag_courses (course_id, tag_id) VALUES (99, 6);
INSERT INTO tag_courses (course_id, tag_id) VALUES (99, 9);


-- Course ID: 100
INSERT INTO courses (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Risk Management Tools & Techniques',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce efficitur sapien vel diam faucibus, eget laoreet sapien lacinia. Duis at erat vitae nisi iaculis feugiat.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1175,
    '2024-04-08',
    '2025-01-28'
);

INSERT INTO course_creators (course_id, creator) VALUES (100, 'Graham Ortega');
INSERT INTO course_creators (course_id, creator) VALUES (100, 'Naomi Bates');

INSERT INTO tag_courses (course_id, tag_id) VALUES (100, 3);
INSERT INTO tag_courses (course_id, tag_id) VALUES (100, 11);
INSERT INTO tag_courses (course_id, tag_id) VALUES (100, 14);
INSERT INTO tag_courses (course_id, tag_id) VALUES (100, 17);

-- Learning Path ID: 2
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Analysis & Forecasting Mastery',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin laoreet libero id neque malesuada, ac aliquet felis tristique. Curabitur ultrices urna at ligula feugiat, eu feugiat justo scelerisque.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1153,
    '2024-02-20',
    '2025-01-15'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (2, 'Emily Carter');
INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (2, 'Jonah Hayes');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (2, 4);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (2, 8);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (2, 9);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (2, 12);


-- Learning Path ID: 3
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Modeling & Excel Masterclass',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam maximus bibendum nisi. Integer vehicula eros eget euismod pretium. Nunc ut mollis metus, id vestibulum nulla.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    981,
    '2024-03-10',
    '2025-01-07'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (3, 'Oliver Singh');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (3, 7);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (3, 13);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (3, 16);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (3, 17);


-- Learning Path ID: 4
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Introduction to Corporate Finance & Valuation',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer a eros posuere, dapibus enim id, scelerisque elit. Fusce luctus tortor a metus mollis, et vestibulum elit interdum.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1390,
    '2024-01-15',
    '2024-11-30'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (4, 'Alice Thompson');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (4, 5);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (4, 12);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (4, 14);


-- Learning Path ID: 5
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Investment Strategies: Equity, Debt, and Alternatives',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur fermentum, enim ut sollicitudin interdum, nunc nisl fermentum purus, id euismod turpis velit eu arcu.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    875,
    '2024-03-08',
    '2025-01-12'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (5, 'Marcus Smith');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (5, 1);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (5, 6);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (5, 8);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (5, 15);


-- Learning Path ID: 6
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Advanced Portfolio Management and Risk Control',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin aliquet felis a nibh sodales, in tincidunt metus tristique. Sed a mauris posuere, ultricies est a, euismod turpis.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1203,
    '2024-04-20',
    '2025-03-03'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (6, 'Liam O’Connor');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (6, 4);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (6, 9);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (6, 14);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (6, 19);


-- Learning Path ID: 7
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Mastering CFA® Level II: Advanced Financial Concepts',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed finibus dui ac ante lobortis tincidunt. Fusce eget ante eu mi vulputate luctus. Mauris ut interdum ligula.',
    'ADVANCED',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    675,
    '2024-05-12',
    '2025-01-28'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (7, 'Sophia Jackson');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (7, 2);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (7, 12);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (7, 17);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (7, 19);


-- Learning Path ID: 8
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Personal Finance & Wealth Building Blueprint',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla id ligula at ipsum scelerisque egestas. Cras viverra elit nec sapien suscipit, ut interdum lorem cursus.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1550,
    '2024-02-02',
    '2024-11-15'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (8, 'Ben Harris');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (8, 3);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (8, 6);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (8, 9);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (8, 16);


-- Learning Path ID: 9
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Corporate Finance: An Executive Guide to Business Finance',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer et eros ac turpis egestas ullamcorper. Phasellus convallis lectus vel dolor ultricies, nec dictum ipsum bibendum.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    823,
    '2024-01-25',
    '2024-10-09'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (9, 'George Patel');
INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (9, 'Nina Foster');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (9, 5);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (9, 7);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (9, 10);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (9, 14);

-- Learning Path ID: 10
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Advanced Investment Strategies: Real Estate & Commodities',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed at justo sit amet felis lacinia fermentum ac non arcu. Curabitur nec nisl bibendum, malesuada libero at, tincidunt metus.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    540,
    '2024-04-15',
    '2025-02-12'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (10, 'Oscar Williams');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (10, 1);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (10, 6);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (10, 8);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (10, 18);


-- Learning Path ID: 11
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Markets: Introduction to Trading & Investment',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet maximus felis, nec laoreet lorem. Sed efficitur enim ut erat pharetra, vel ullamcorper leo hendrerit.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1458,
    '2024-03-30',
    '2025-01-17'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (11, 'Carla Miller');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (11, 4);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (11, 5);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (11, 11);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (11, 16);


-- Learning Path ID: 12
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'FinTech Foundations: Blockchain, Crypto, & Digital Payments',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla finibus, libero at cursus lacinia, odio ipsum cursus libero, sit amet aliquam elit purus eget nunc.',
    'INTERMEDIATE',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    872,
    '2024-02-18',
    '2024-12-08'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (12, 'Adam Porter');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (12, 3);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (12, 7);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (12, 14);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (12, 17);


-- Learning Path ID: 13
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Financial Risk Management: Methods & Tools',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sit amet nulla eget turpis pretium fringilla. Mauris sed dui sit amet lectus condimentum tempor.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1023,
    '2024-03-03',
    '2025-02-07'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (13, 'Liam Simmons');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (13, 2);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (13, 6);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (13, 8);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (13, 10);


-- Learning Path ID: 14
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Quantitative Finance: Models & Algorithms',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Donec suscipit tincidunt diam, et egestas neque facilisis sit amet. Fusce at sem nunc.',
    'ADVANCED',
    'https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    711,
    '2024-04-07',
    '2025-01-25'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (14, 'Vera Nguyen');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (14, 9);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (14, 15);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (14, 19);


-- Learning Path ID: 15
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Mastering Financial Statements & Ratio Analysis',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla rutrum lectus eu turpis suscipit, sed hendrerit velit consequat. Aliquam erat volutpat. Cras id velit felis.',
    'BEGINNER',
    'https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1224,
    '2024-02-03',
    '2024-10-29'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (15, 'Ethan Brooks');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (15, 5);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (15, 10);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (15, 13);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (15, 18);


-- Learning Path ID: 16
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Corporate Financial Planning & Strategy',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer feugiat magna at risus fermentum, ac vehicula libero cursus. Nullam ac enim scelerisque, volutpat justo in, dictum ligula.',
    'INTERMEDIATE',
    'https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    1345,
    '2024-05-01',
    '2025-03-02'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (16, 'Cameron Davis');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (16, 1);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (16, 6);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (16, 12);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (16, 14);


-- Learning Path ID: 17
INSERT INTO learning_paths (
    title,
    description,
    difficulty,
    iconurl,
    number_of_enrolled_users,
    created_at,
    last_updated_at
) VALUES (
    'Advanced Accounting & Financial Reporting',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla ac lorem in libero vulputate hendrerit. Curabitur a volutpat risus. Pellentesque habitant morbi tristique senectus et netus.',
    'ADVANCED',
    'https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?q=80&w=1555&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    983,
    '2024-03-27',
    '2025-01-09'
);

INSERT INTO learning_path_creators (learning_path_id, creator) VALUES (17, 'Sofia Grant');

INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (17, 2);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (17, 7);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (17, 10);
INSERT INTO tag_learning_paths (learning_path_id, tag_id) VALUES (17, 19);


-- User Progress
INSERT INTO question_progress_trackers (question_id, total_count, correct_count, correct_streak, accuracy, first_reviewed_date, last_reviewed_date, next_review_date, average_question_time, repetitions, easiness_factor, interval, user_rating)
VALUES
(1, 12, 9, 5, 75, '2025-02-10', '2025-04-10', '2025-10-29', 25000, 9, 2.0, 8, 4),
(2, 11, 5, 2, 45.45, '2025-05-08', '2025-06-11', '2025-10-29', 54692, 5, 1.85, 1, 3),
(3, 12, 4, 2, 33.33, '2025-05-08', '2025-06-02', '2025-10-29', 39031, 4, 1.7, 6, 4),
(4, 15, 11, 9, 73.33, '2025-04-25', '2025-06-01', '2025-06-27', 119947, 11, 2.18, 7, 5),
(5, 17, 3, 1, 17.65, '2025-04-08', '2025-05-13', '2025-10-28', 115557, 3, 1.51, 7, 0),
(6, 14, 10, 5, 71.43, '2025-02-09', '2025-02-24', '2025-10-29', 62092, 10, 2.16, 2, 3),
(7, 18, 5, 4, 27.78, '2025-03-04', '2025-05-12', '2025-06-17', 11396, 5, 1.63, 5, 3),
(8, 10, 7, 5, 70.0, '2025-04-27', '2025-06-06', '2025-10-29', 26480, 7, 2.14, 4, 3),
(9, 17, 5, 1, 29.41, '2025-03-22', '2025-06-08', '2025-06-25', 31584, 5, 1.67, 2, 1),
(10, 5, 3, 1, 60.0, '2025-05-01', '2025-06-01', '2025-10-29', 35319, 3, 2.02, 1, 2),
(11, 6, 3, 1, 50.0, '2025-04-15', '2025-05-20', '2025-06-09', 52487, 3, 1.9, 6, 1),
(12, 19, 11, 7, 57.89, '2025-03-15', '2025-06-12', '2025-10-29', 118252, 11, 2.02, 4, 4),
(13, 14, 11, 8, 78.57, '2025-04-06', '2025-06-10', '2025-06-20', 60304, 11, 2.26, 7, 5),
(14, 4, 2, 1, 50.0, '2025-03-06', '2025-05-03', '2025-10-29', 7454, 2, 1.9, 7, 1),
(15, 18, 14, 8, 77.78, '2025-03-12', '2025-04-13', '2025-05-19', 106635, 14, 2.25, 3, 4),
(16, 8, 6, 2, 75.0, '2025-04-03', '2025-06-01', '2025-10-29', 26384, 6, 2.2, 4, 2),
(17, 7, 5, 4, 71.43, '2025-04-21', '2025-06-08', '2025-10-29', 98475, 5, 2.16, 8, 4),
(18, 11, 7, 5, 63.64, '2025-02-08', '2025-03-29', '2025-10-29', 25269, 7, 2.07, 6, 3),
(19, 12, 2, 1, 16.67, '2025-03-14', '2025-04-18', '2025-05-03', 107973, 2, 1.49, 9, 1),
(20, 10, 9, 5, 90.0, '2025-05-14', '2025-06-09', '2025-06-18', 90123, 9, 2.42, 3, 4),
(21, 16, 10, 6, 62.5, '2025-03-17', '2025-05-24', '2025-10-29', 40650, 10, 2.06, 5, 3),
(22, 8, 4, 1, 50.0, '2025-04-26', '2025-06-07', '2025-10-29', 93061, 4, 1.9, 2, 1),
(23, 17, 14, 11, 82.35, '2025-02-27', '2025-04-10', '2025-05-18', 88020, 14, 2.32, 8, 5),
(24, 18, 12, 9, 66.67, '2025-02-14', '2025-04-18', '2025-10-29', 100297, 12, 2.11, 4, 4),
(25, 15, 3, 2, 20.0, '2025-05-08', '2025-06-10', '2025-10-29', 119891, 3, 1.54, 5, 2),
(26, 13, 6, 1, 46.15, '2025-03-13', '2025-04-22', '2025-05-20', 108351, 6, 1.84, 7, 1),
(27, 6, 4, 2, 66.67, '2025-04-01', '2025-06-06', '2025-10-29', 98324, 4, 2.11, 3, 3),
(28, 19, 13, 9, 68.42, '2025-02-20', '2025-03-29', '2025-04-22', 93934, 13, 2.13, 6, 4),
(29, 5, 3, 1, 60.0, '2025-04-10', '2025-05-01', '2025-10-29', 109742, 3, 2.02, 2, 1),
(30, 14, 11, 9, 78.57, '2025-01-31', '2025-03-17', '2025-10-28', 73691, 11, 2.26, 4, 5),
(31, 9, 3, 2, 33.33, '2025-03-29', '2025-05-25', '2025-10-29', 86985, 3, 1.7, 2, 2),
(32, 10, 6, 3, 60.0, '2025-02-02', '2025-03-22', '2025-10-29', 17980, 6, 2.02, 7, 3),
(33, 11, 8, 5, 72.73, '2025-04-13', '2025-05-13', '2025-10-29', 50643, 8, 2.17, 5, 3),
(34, 19, 12, 6, 63.16, '2025-02-23', '2025-04-06', '2025-05-05', 65864, 12, 2.08, 6, 3),
(35, 13, 8, 6, 61.54, '2025-05-04', '2025-05-31', '2025-10-29', 26271, 8, 2.05, 1, 3),
(36, 17, 15, 12, 88.24, '2025-01-18', '2025-02-28', '2025-03-19', 71142, 15, 2.39, 9, 5),
(37, 16, 4, 2, 25.0, '2025-01-12', '2025-02-21', '2025-10-28', 104774, 4, 1.61, 2, 1),
(38, 18, 13, 9, 72.22, '2025-01-08', '2025-03-05', '2025-10-29', 10824, 13, 2.17, 9, 4),
(39, 15, 9, 6, 60.0, '2025-01-22', '2025-03-07', '2025-10-29', 44880, 9, 2.02, 8, 3),
(40, 11, 4, 2, 36.36, '2025-03-04', '2025-04-09', '2025-10-29', 109060, 4, 1.73, 3, 2),
(41, 18, 15, 12, 83.33, '2025-03-30', '2025-04-23', '2025-05-07', 44974, 15, 2.34, 3, 5),
(42, 17, 11, 9, 64.71, '2025-01-17', '2025-03-28', '2025-10-29', 85566, 11, 2.1, 2, 4),
(43, 12, 7, 6, 58.33, '2025-04-12', '2025-05-25', '2025-06-13', 35085, 7, 2.01, 5, 4),
(44, 10, 4, 2, 40.0, '2025-01-19', '2025-03-09', '2025-04-02', 7122, 4, 1.78, 4, 2),
(45, 13, 10, 7, 76.92, '2025-02-06', '2025-03-16', '2025-10-29', 107996, 10, 2.23, 7, 4),
(46, 11, 5, 2, 45.45, '2025-03-02', '2025-04-15', '2025-05-09', 117253, 5, 1.85, 3, 2),
(47, 6, 3, 1, 50.0, '2025-01-10', '2025-02-21', '2025-03-13', 113774, 3, 1.9, 1, 1),
(48, 14, 9, 6, 64.29, '2025-04-04', '2025-05-19', '2025-10-29', 8395, 9, 2.09, 2, 3),
(49, 9, 6, 3, 66.67, '2025-03-01', '2025-04-28', '2025-10-28', 105275, 6, 2.11, 8, 3),
(50, 16, 14, 10, 87.5, '2025-01-15', '2025-02-25', '2025-10-29', 42214, 14, 2.38, 6, 5),
(51, 18, 8, 3, 44.44, '2025-04-17', '2025-05-26', '2025-06-18', 56182, 8, 1.84, 1, 2),
(52, 14, 11, 8, 78.57, '2025-01-30', '2025-03-01', '2025-10-29', 33194, 11, 2.26, 4, 4),
(53, 13, 6, 3, 46.15, '2025-02-25', '2025-03-27', '2025-10-29', 8237, 6, 1.84, 6, 2),
(54, 17, 12, 7, 70.59, '2025-03-25', '2025-04-29', '2025-05-18', 62859, 12, 2.15, 3, 3),
(55, 0, 0, 0, 40.0, '2025-02-08', '2025-04-05', '2025-10-28', 25933, 2, 1.78, 9, 1),
(56, 0, 0, 0, 68.75, '2025-01-28', '2025-03-26', '2025-04-21', 26836, 11, 2.12, 7, 4),
(57, 0, 0, 0, 40.0, '2025-03-07', '2025-04-11', '2025-10-29', 68084, 6, 1.78, 5, 2),
(58, 0, 0, 0, 38.89, '2025-03-03', '2025-04-30', '2025-10-29', 72595, 7, 1.76, 2, 3),
(59, 0, 0, 0, 42.86, '2025-04-07', '2025-05-14', '2025-10-28', 18588, 3, 1.81, 4, 1),
(60, 0, 0, 0, 68.42, '2025-03-05', '2025-05-02', '2025-10-28', 98061, 13, 2.13, 6, 4),
(61, 0, 0, 0, 69.23, '2025-02-19', '2025-04-14', '2025-10-29', 42850, 9, 2.14, 3, 4),
(62, 0, 0, 0, 57.14, '2025-04-02', '2025-05-21', '2025-06-06', 29578, 8, 2.01, 2, 3),
(63, 0, 0, 0, 50.0, '2025-01-24', '2025-03-03', '2025-10-29', 81263, 6, 1.9, 7, 2),
(64, 0, 0, 0, 70.59, '2025-03-15', '2025-04-24', '2025-05-19', 33412, 12, 2.15, 5, 4),
(65, 0, 0, 0, 31.25, '2025-04-08', '2025-05-17', '2025-10-29', 49987, 5, 1.66, 2, 2),
(66, 0, 0, 0, 33.33, '2025-02-01', '2025-03-25', '2025-10-29', 64112, 2, 1.7, 3, 1),
(67, 0, 0, 0, 73.33, '2025-01-29', '2025-03-09', '2025-10-29', 47325, 11, 2.2, 8, 5),
(68, 0, 0, 0, 54.55, '2025-04-10', '2025-05-14', '2025-10-29', 100332, 6, 1.95, 4, 2),
(69, 0, 0, 0, 70.0, '2025-03-05', '2025-04-09', '2025-10-28', 38859, 7, 2.14, 6, 4),
(70, 0, 0, 0, 55.56, '2025-02-21', '2025-04-15', '2025-10-29', 117589, 5, 1.96, 7, 3),
(71, 0, 0, 0, 89.47, '2025-03-03', '2025-04-22', '2025-10-29', 102341, 17, 2.41, 4, 5),
(72, 0, 0, 0, 33.33, '2025-02-26', '2025-03-27', '2025-10-29', 71550, 4, 1.7, 6, 1),
(73, 0, 0, 0, 71.43, '2025-03-07', '2025-04-29', '2025-10-29', 85362, 5, 2.16, 5, 3),
(74, 0, 0, 0, 85.71, '2025-01-14', '2025-03-10', '2025-10-29', 69307, 12, 2.36, 6, 5),
(75, 0, 0, 0, 81.25, '2025-04-01', '2025-05-14', '2025-10-29', 30650, 13, 2.31, 7, 5),
(76, 0, 0, 0, 50.0, '2025-02-28', '2025-04-21', '2025-10-29', 47710, 9, 1.9, 3, 3),
(77, 0, 0, 0, 60.0, '2025-03-20', '2025-05-01', '2025-10-29', 39641, 3, 2.02, 2, 2),
(78, 0, 0, 0, 75.0, '2025-04-16', '2025-06-01', '2025-10-29', 51132, 6, 2.2, 5, 4),
(79, 0, 0, 0, 88.24, '2025-01-21', '2025-05-20', '2025-06-06', 75320, 15, 2.39, 6, 5);


INSERT INTO lesson_progress_trackers (lesson_id, streak, total_questions_reviewed, accuracy, average_question_time, questions_due_today, first_practice_date, last_practice_date, completeness, mastery)
VALUES
(7, 11, 154, 56.26, 65080, 0, '2025-01-17', '2025-06-11', 0.3, 0.7),
(8, 0, 119, 52.77, 57554, 0, '2025-01-10', '2025-06-08', 1.0, 0.8),
(9, 1, 146, 60.59, 49423, 0, '2025-01-15', '2025-06-12', 0.1, 0.4),
(10, 8, 139, 56.87, 66047, 0, '2025-01-28', '2025-06-09', 1.0, 0.3),
(11, 5, 149, 51.31, 74778, 0, '2025-01-24', '2025-06-10', 0.2, 0.2),
(12, 1, 136, 62.28, 79751, 0, '2025-01-31', '2025-05-25', 1.0, 0.7),
(13, 0, 161, 68.59, 61967, 0, '2025-01-08', '2025-06-01', 0.3, 0.4);

INSERT INTO course_progress_trackers (course_id, is_active, streak, total_questions_reviewed, accuracy, average_question_time, questions_due_today, first_practice_date, last_practice_date)
VALUES
(1, true, 7, 1004, 58.38, 64943, 0, '2025-01-08', '2025-06-12'),
(8, true, 4, 1004, 58.38, 64943, 0, '2025-01-08', '2025-06-12'),
(17, true, 0, 1004, 58.38, 64943, 0, '2025-01-08', '2025-06-12'),
(99, true, 0, 1004, 58.38, 64943, 0, '2025-01-08', '2025-06-12'),
(20, true, 1, 1004, 58.38, 64943, 0, '2025-01-08', '2025-06-12'),
(88, true, 9, 1004, 58.38, 64943, 0, '2025-01-08', '2025-06-12'),
(77, true, 14, 1004, 58.38, 64943, 0, '2025-01-08', '2025-06-12');

INSERT INTO user_progress_trackers (streak, longest_streak, most_recent_course_tracker_id) VALUES (6, 12, 1);

UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 1;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 2;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 3;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 4;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 5;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 6;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 39;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 40;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 41;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 42;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 1 WHERE id = 43;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 7;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 8;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 9;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 10;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 11;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 44;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 45;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 46;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 47;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 48;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 2 WHERE id = 49;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 12;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 13;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 14;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 15;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 16;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 50;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 51;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 52;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 53;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 54;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 3 WHERE id = 55;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 17;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 18;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 19;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 20;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 21;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 22;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 56;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 57;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 58;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 59;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 60;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 4 WHERE id = 61;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 23;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 24;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 25;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 26;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 27;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 62;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 63;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 64;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 65;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 66;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 5 WHERE id = 67;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 28;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 29;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 30;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 31;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 32;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 33;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 68;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 69;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 70;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 71;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 72;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 6 WHERE id = 73;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 34;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 35;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 36;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 37;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 38;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 74;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 75;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 76;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 77;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 78;
UPDATE question_progress_trackers SET lesson_progress_tracker_id = 7 WHERE id = 79;

UPDATE lesson_progress_trackers SET course_progress_tracker_id = 1 WHERE id = 1;
UPDATE lesson_progress_trackers SET course_progress_tracker_id = 1 WHERE id = 2;
UPDATE lesson_progress_trackers SET course_progress_tracker_id = 1 WHERE id = 3;
UPDATE lesson_progress_trackers SET course_progress_tracker_id = 1 WHERE id = 4;
UPDATE lesson_progress_trackers SET course_progress_tracker_id = 1 WHERE id = 5;
UPDATE lesson_progress_trackers SET course_progress_tracker_id = 1 WHERE id = 6;
UPDATE lesson_progress_trackers SET course_progress_tracker_id = 1 WHERE id = 7;

UPDATE course_progress_trackers SET user_progress_tracker_id = 1 WHERE id = 1;
UPDATE course_progress_trackers SET user_progress_tracker_id = 1 WHERE id = 2;
UPDATE course_progress_trackers SET user_progress_tracker_id = 1 WHERE id = 3;
UPDATE course_progress_trackers SET user_progress_tracker_id = 1 WHERE id = 4;
UPDATE course_progress_trackers SET user_progress_tracker_id = 1 WHERE id = 5;
UPDATE course_progress_trackers SET user_progress_tracker_id = 1 WHERE id = 6;
UPDATE course_progress_trackers SET user_progress_tracker_id = 1 WHERE id = 7;

UPDATE users SET user_progress_tracker_id = 1 where id = 1;


-- QuestionReview
INSERT INTO question_reviews (user_progress_tracker_id, question_progress_tracker_id, question_time, correct, user_rating, review_date)
VALUES
-- For tracker_id = 1
(1, 1, 10000, true, 4, '2025-03-01'),
(1, 1, 24000, true, 4, '2025-02-15'),
(1, 1, 26000, false, 3, '2025-03-01'),
(1, 1, 22000, true, 4, '2025-03-15'),
(1, 1, 28000, true, 5, '2025-09-01'),
(1, 1, 26000, true, 4, '2025-09-10'),

-- For tracker_id = 2
(1, 2, 50000, false, 2, '2025-05-09'),
(1, 2, 62000, true, 3, '2025-05-20'),
(1, 2, 53000, false, 3, '2025-05-30'),
(1, 2, 55000, true, 4, '2025-09-05'),
(1, 2, 59000, false, 3, '2025-09-11'),

-- For tracker_id = 3
(1, 3, 40000, false, 2, '2025-05-09'),
(1, 3, 37000, true, 3, '2025-05-25'),
(1, 3, 41000, false, 2, '2025-09-30'),
(1, 3, 38500, true, 3, '2025-06-01'),

-- For tracker_id = 4
(1, 4, 115000, true, 4, '2025-04-26'),
(1, 4, 121000, true, 5, '2025-05-10'),
(1, 4, 123000, true, 5, '2025-09-20'),
(1, 4, 118000, false, 3, '2025-05-28'),
(1, 4, 122000, true, 4, '2025-06-01'),

-- For tracker_id = 5
(1, 5, 110000, false, 1, '2025-04-10'),
(1, 5, 118000, false, 2, '2025-04-28'),
(1, 5, 119000, true, 3, '2025-09-13'),

-- For tracker_id = 6
(1, 6, 60000, true, 3, '2025-02-10'),
(1, 6, 64000, false, 2, '2025-02-14'),
(1, 6, 62000, true, 4, '2025-02-18'),
(1, 6, 61000, true, 4, '2025-09-22'),
(1, 6, 63000, true, 5, '2025-02-24'),

-- For tracker_id = 7
(1, 7, 12000, false, 3, '2025-03-10'),
(1, 7, 11500, false, 2, '2025-04-02'),
(1, 7, 11800, true, 3, '2025-04-25'),
(1, 7, 11000, true, 3, '2025-09-10'),
(1, 7, 10500, false, 2, '2025-09-12'),

-- For tracker_id = 8
(1, 8, 26000, true, 3, '2025-04-28'),
(1, 8, 27000, true, 3, '2025-05-15'),
(1, 8, 26500, false, 2, '2025-05-28'),
(1, 8, 26000, true, 3, '2025-06-06'),

-- For tracker_id = 9
(1, 9, 31000, false, 1, '2025-03-23'),
(1, 9, 32000, false, 2, '2025-04-15'),
(1, 9, 30000, true, 3, '2025-09-30'),
(1, 9, 31500, true, 2, '2025-09-08'),

-- For tracker_id = 10
(1, 10, 35000, true, 3, '2025-05-05'),
(1, 10, 36000, false, 2, '2025-05-20'),
(1, 10, 35000, true, 3, '2025-09-01'),
-- Tracker 11
(1, 11, 53000, true, 2, '2025-04-20'),
(1, 11, 51000, false, 1, '2025-05-01'),
(1, 11, 53000, true, 2, '2025-05-20'),

-- Tracker 12
(1, 12, 120000, true, 4, '2025-03-20'),
(1, 12, 117000, false, 3, '2025-04-15'),
(1, 12, 119500, true, 4, '2025-05-01'),
(1, 12, 118300, true, 4, '2025-09-20'),
(1, 12, 116000, false, 3, '2025-09-01'),
(1, 12, 118900, true, 5, '2025-09-12'),

-- Tracker 13
(1, 13, 60000, true, 5, '2025-04-10'),
(1, 13, 61000, true, 4, '2025-04-22'),
(1, 13, 59500, false, 3, '2025-05-15'),
(1, 13, 60500, true, 4, '2025-06-01'),
(1, 13, 61000, true, 5, '2025-06-10'),

-- Tracker 14
(1, 14, 7000, true, 2, '2025-03-10'),
(1, 14, 7900, false, 1, '2025-04-05'),
(1, 14, 7500, false, 1, '2025-09-03'),

-- Tracker 15
(1, 15, 104000, true, 4, '2025-03-15'),
(1, 15, 107000, true, 5, '2025-03-25'),
(1, 15, 106500, false, 3, '2025-04-01'),
(1, 15, 108000, true, 4, '2025-09-10'),
(1, 15, 106000, true, 4, '2025-09-13'),

-- Tracker 16
(1, 16, 26000, true, 3, '2025-04-10'),
(1, 16, 27000, false, 2, '2025-05-01'),
(1, 16, 26500, true, 3, '2025-05-20'),
(1, 16, 25500, true, 4, '2025-06-01'),

-- Tracker 17
(1, 17, 97000, true, 3, '2025-04-21'),
(1, 17, 98000, false, 2, '2025-05-05'),
(1, 17, 99000, true, 4, '2025-05-25'),
(1, 17, 98500, true, 4, '2025-06-08'),

-- Tracker 18
(1, 18, 25000, false, 2, '2025-02-10'),
(1, 18, 26000, true, 4, '2025-02-25'),
(1, 18, 24000, false, 3, '2025-03-10'),
(1, 18, 25500, true, 4, '2025-03-20'),
(1, 18, 25000, true, 3, '2025-03-29'),

-- Tracker 19
(1, 19, 107000, false, 1, '2025-03-20'),
(1, 19, 109000, false, 2, '2025-04-01'),
(1, 19, 108500, true, 3, '2025-04-18'),

-- Tracker 20
(1, 20, 90000, true, 5, '2025-05-15'),
(1, 20, 91000, true, 4, '2025-05-25'),
(1, 20, 89500, true, 5, '2025-09-01'),
(1, 20, 88500, true, 5, '2025-09-09'),

-- Tracker 21
(1, 21, 40500, true, 3, '2025-03-20'),
(1, 21, 40000, false, 2, '2025-04-05'),
(1, 21, 41000, true, 3, '2025-04-20'),
(1, 21, 40500, true, 3, '2025-05-10'),
(1, 21, 40800, false, 2, '2025-09-24'),

-- Tracker 22
(1, 22, 94000, false, 1, '2025-04-27'),
(1, 22, 92000, true, 3, '2025-05-20'),
(1, 22, 92500, false, 2, '2025-09-07'),

-- Tracker 23
(1, 23, 88000, true, 5, '2025-02-28'),
(1, 23, 89000, true, 5, '2025-03-10'),
(1, 23, 87500, false, 3, '2025-03-20'),
(1, 23, 88500, true, 5, '2025-09-01'),
(1, 23, 88000, true, 5, '2025-09-10'),

-- Tracker 24
(1, 24, 100000, false, 2, '2025-02-20'),
(1, 24, 101000, true, 4, '2025-03-05'),
(1, 24, 100500, false, 2, '2025-03-25'),
(1, 24, 99500, true, 3, '2025-09-10'),
(1, 24, 102000, true, 4, '2025-09-18'),

-- Tracker 25
(1, 25, 120000, false, 1, '2025-05-10'),
(1, 25, 119500, false, 2, '2025-09-25'),
(1, 25, 120500, true, 3, '2025-09-10'),

-- Tracker 26
(1, 26, 108000, false, 1, '2025-03-15'),
(1, 26, 109000, true, 3, '2025-04-01'),
(1, 26, 108500, false, 2, '2025-04-22'),

-- Tracker 27
(1, 27, 98000, true, 4, '2025-04-10'),
(1, 27, 98500, false, 2, '2025-05-10'),
(1, 27, 99000, true, 3, '2025-06-06'),

-- Tracker 28
(1, 28, 94000, true, 4, '2025-02-22'),
(1, 28, 94500, false, 2, '2025-03-01'),
(1, 28, 93000, true, 4, '2025-09-15'),
(1, 28, 92000, true, 5, '2025-09-29'),

-- Tracker 29
(1, 29, 109000, true, 3, '2025-04-12'),
(1, 29, 110000, false, 1, '2025-04-25'),
(1, 29, 109500, true, 2, '2025-09-01'),

-- Tracker 30
(1, 30, 74000, true, 5, '2025-02-10'),
(1, 30, 73000, true, 5, '2025-02-28'),
(1, 30, 73500, false, 3, '2025-03-10'),
(1, 30, 74000, true, 5, '2025-03-17'),
-- Tracker 31
(1, 31, 86000, false, 2, '2025-03-30'),
(1, 31, 87000, true, 3, '2025-04-18'),
(1, 31, 87500, false, 2, '2025-05-25'),

-- Tracker 32
(1, 32, 18000, true, 4, '2025-02-05'),
(1, 32, 17500, true, 3, '2025-02-20'),
(1, 32, 18500, false, 2, '2025-03-10'),
(1, 32, 17900, true, 3, '2025-03-22'),

-- Tracker 33
(1, 33, 50500, true, 4, '2025-04-13'),
(1, 33, 50000, false, 2, '2025-04-25'),
(1, 33, 51000, true, 4, '2025-09-05'),
(1, 33, 50800, true, 4, '2025-09-13'),

-- Tracker 34
(1, 34, 66000, true, 3, '2025-02-23'),
(1, 34, 65500, false, 2, '2025-03-10'),
(1, 34, 65800, true, 3, '2025-09-30'),
(1, 34, 66000, true, 4, '2025-09-06'),

-- Tracker 35
(1, 35, 26000, false, 2, '2025-05-04'),
(1, 35, 26500, true, 3, '2025-05-15'),
(1, 35, 26300, false, 2, '2025-05-31'),

-- Tracker 36
(1, 36, 71000, true, 5, '2025-01-18'),
(1, 36, 71500, true, 5, '2025-02-01'),
(1, 36, 70500, false, 3, '2025-02-14'),
(1, 36, 70800, true, 5, '2025-02-28'),

-- Tracker 37
(1, 37, 104000, false, 1, '2025-01-12'),
(1, 37, 105000, false, 2, '2025-09-01'),
(1, 37, 104800, false, 2, '2025-09-21'),

-- Tracker 38
(1, 38, 11000, true, 4, '2025-01-10'),
(1, 38, 10500, false, 3, '2025-02-01'),
(1, 38, 10800, true, 4, '2025-09-20'),
(1, 38, 10900, true, 5, '2025-09-05'),

-- Tracker 39
(1, 39, 45000, true, 3, '2025-01-25'),
(1, 39, 45500, false, 2, '2025-09-15'),
(1, 39, 44800, true, 3, '2025-09-07'),

-- Tracker 40
(1, 40, 109000, false, 2, '2025-03-10'),
(1, 40, 108500, false, 1, '2025-03-25'),
(1, 40, 109500, true, 2, '2025-09-09'),

-- Tracker 41
(1, 41, 45000, true, 5, '2025-03-30'),
(1, 41, 45500, true, 4, '2025-09-10'),
(1, 41, 44900, true, 5, '2025-09-23'),

-- Tracker 42
(1, 42, 85000, false, 3, '2025-01-20'),
(1, 42, 86000, true, 4, '2025-02-10'),
(1, 42, 85500, false, 2, '2025-03-10'),
(1, 42, 85800, true, 4, '2025-03-28'),

-- Tracker 43
(1, 43, 35000, false, 3, '2025-04-12'),
(1, 43, 35500, true, 4, '2025-04-25'),
(1, 43, 35200, false, 2, '2025-09-10'),
(1, 43, 35100, true, 4, '2025-09-25'),

-- Tracker 44
(1, 44, 7000, false, 1, '2025-01-19'),
(1, 44, 7200, true, 2, '2025-02-10'),
(1, 44, 7100, false, 2, '2025-09-09'),

-- Tracker 45
(1, 45, 108000, true, 5, '2025-02-06'),
(1, 45, 108500, false, 3, '2025-02-20'),
(1, 45, 107800, true, 4, '2025-03-10'),
(1, 45, 108200, true, 5, '2025-03-16'),

-- Tracker 46
(1, 46, 117000, false, 2, '2025-03-02'),
(1, 46, 117500, true, 3, '2025-03-20'),
(1, 46, 117200, false, 2, '2025-04-15'),

-- Tracker 47
(1, 47, 113500, true, 3, '2025-01-10'),
(1, 47, 114000, false, 1, '2025-02-01'),
(1, 47, 113700, false, 2, '2025-02-21'),

-- Tracker 48
(1, 48, 8300, true, 4, '2025-04-04'),
(1, 48, 8500, false, 2, '2025-04-25'),
(1, 48, 8400, true, 3, '2025-05-10'),
(1, 48, 8350, true, 4, '2025-05-19'),

-- Tracker 49
(1, 49, 105000, true, 3, '2025-03-05'),
(1, 49, 105500, false, 2, '2025-04-10'),
(1, 49, 105300, true, 4, '2025-04-28'),

-- Tracker 50
(1, 50, 42000, true, 5, '2025-01-15'),
(1, 50, 42500, true, 4, '2025-01-30'),
(1, 50, 41800, false, 3, '2025-02-10'),
(1, 50, 42300, true, 5, '2025-02-25'),

(1, 51, 18343, true, 3, '2025-06-11'),
(1, 51, 27823, false, 3, '2025-05-01'),
(1, 51, 25403, false, 2, '2025-04-24'),
(1, 51, 29331, false, 2, '2025-09-07'),
(1, 51, 23263, true, 3, '2025-09-30'),
(1, 51, 19742, false, 2, '2025-09-21'),

(1, 52, 17561, false, 2, '2025-03-17'),
(1, 52, 24810, false, 4, '2025-03-07'),
(1, 52, 18949, true, 5, '2025-02-06'),
(1, 52, 26369, true, 3, '2025-09-26'),
(1, 52, 27903, true, 3, '2025-09-27'),

(1, 53, 23202, true, 1, '2025-04-17'),
(1, 53, 23109, false, 1, '2025-04-22'),
(1, 53, 26392, false, 2, '2025-04-26'),
(1, 53, 19521, true, 1, '2025-04-01'),
(1, 53, 15513, false, 1, '2025-03-26'),
(1, 53, 27145, true, 2, '2025-09-04'),
(1, 53, 22659, false, 2, '2025-09-26'),

(1, 54, 27714, true, 1, '2025-05-08'),
(1, 54, 27528, true, 5, '2025-03-29'),
(1, 54, 29323, true, 2, '2025-04-13'),
(1, 54, 22307, true, 2, '2025-04-30'),
(1, 54, 22180, true, 2, '2025-09-18'),
(1, 54, 22839, true, 3, '2025-09-04');

-- Test for Section 2
INSERT INTO tests (id, number_of_questions, time_limit)
VALUES (1, 10, 300);

UPDATE sections SET test_id = 1 WHERE id = 2;

INSERT INTO test_questions (test_id, question_id)
VALUES
(1, 1),
(1, 74),
(1, 63),
(1, 37),
(1, 18),
(1, 77),
(1, 13),
(1, 44),
(1, 8),
(1, 20);


-- USERS ENROLLED COURSES
INSERT INTO user_courses (user_id, course_id) VALUES
(1, 1),
(1, 8),
(1, 17),
(1, 20),
(1, 77),
(1, 88),
(1, 99);

INSERT INTO user_learning_paths (user_id, learning_path_id) VALUES
(1, 1),
(1, 7),
(1, 16);


-- AGGREGATE QUESTION TRACKERS
INSERT INTO aggregate_question_trackers (question_id, accuracy, number_of_attempts, average_question_time) VALUES
(1, 41.34, 343, 18000),
(2, 72.15, 1533, 67213),
(3, 18.45, 456, 35910),
(4, 90.22, 2230, 48567),
(5, 64.26, 2677, 91422),
(6, 22.10, 1089, 37109),
(7, 79.89, 894, 77451),
(8, 12.29, 2933, 22143),
(9, 95.30, 300, 64322),
(10, 55.23, 2344, 98114),
(11, 48.14, 1409, 75831),
(12, 33.21, 213, 41150),
(13, 84.27, 2766, 56922),
(14, 67.17, 1789, 80741),
(15, 90.29, 2956, 28234),
(16, 71.13, 1322, 55710),
(17, 25.98, 986, 32841),
(18, 57.25, 2565, 97822),
(19, 92.26, 2687, 60123),
(20, 47.11, 114, 38910),
(21, 63.21, 2134, 71999),
(22, 34.77, 77, 27234),
(23, 99.28, 2870, 88567),
(24, 50.74, 745, 65544),
(25, 88.21, 2198, 79011),
(26, 46.18, 188, 42009),
(27, 31.16, 1655, 38234),
(28, 61.24, 2441, 91422),
(29, 82.30, 305, 77745),
(30, 16.29, 2900, 18822),
(31, 54.17, 178, 35411),
(32, 39.99, 999, 63422),
(33, 24.28, 2888, 19877),
(34, 87.26, 2666, 94722),
(35, 51.11, 112, 47334),
(36, 78.24, 2489, 88219),
(37, 43.10, 1044, 57123),
(38, 20.15, 1512, 26345),
(39, 99.30, 3000, 99999),
(40, 66.90, 909, 71881),
(41, 52.25, 2543, 62012),
(42, 47.98, 987, 35999),
(43, 29.12, 1222, 40123),
(44, 82.16, 1644, 87101),
(45, 68.13, 1311, 79090),
(46, 35.23, 2309, 32012),
(47, 59.14, 1456, 69124),
(48, 74.18, 1889, 73411),
(49, 45.99, 991, 42144),
(50, 93.25, 2577, 94122),
(51, 37.78, 78, 27654),
(52, 89.27, 2766, 80441),
(53, 60.16, 1640, 56324),
(54, 12.84, 844, 23044),
(55, 77.29, 2901, 87641),
(56, 33.18, 187, 30567),
(57, 96.29, 2990, 99500),
(58, 58.17, 1764, 74432),
(59, 26.13, 1302, 33781),
(60, 53.19, 1999, 61512),
(61, 42.15, 154, 48901),
(62, 85.26, 2660, 90321),
(63, 63.12, 1255, 76450),
(64, 91.24, 2431, 81345),
(65, 19.53, 532, 24910),
(66, 46.16, 1654, 55121),
(67, 32.94, 945, 38899),
(68, 70.27, 2730, 70214),
(69, 27.11, 1143, 32111),
(70, 81.21, 2109, 86190),
(71, 49.67, 677, 47892),
(72, 94.28, 2855, 99010),
(73, 23.10, 1009, 31632),
(74, 56.19, 1988, 68045),
(75, 79.22, 2288, 77101),
(76, 40.15, 1543, 41988),
(77, 17.13, 134, 18444),
(78, 62.18, 1888, 68911),
(79, 88.29, 2999, 95321);

UPDATE question_progress_trackers SET aggregate_question_tracker_id = 1 WHERE question_id = 1;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 2 WHERE question_id = 2;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 3 WHERE question_id = 3;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 4 WHERE question_id = 4;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 5 WHERE question_id = 5;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 6 WHERE question_id = 6;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 7 WHERE question_id = 7;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 8 WHERE question_id = 8;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 9 WHERE question_id = 9;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 10 WHERE question_id = 10;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 11 WHERE question_id = 11;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 12 WHERE question_id = 12;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 13 WHERE question_id = 13;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 14 WHERE question_id = 14;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 15 WHERE question_id = 15;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 16 WHERE question_id = 16;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 17 WHERE question_id = 17;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 18 WHERE question_id = 18;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 19 WHERE question_id = 19;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 20 WHERE question_id = 20;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 21 WHERE question_id = 21;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 22 WHERE question_id = 22;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 23 WHERE question_id = 23;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 24 WHERE question_id = 24;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 25 WHERE question_id = 25;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 26 WHERE question_id = 26;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 27 WHERE question_id = 27;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 28 WHERE question_id = 28;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 29 WHERE question_id = 29;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 30 WHERE question_id = 30;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 31 WHERE question_id = 31;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 32 WHERE question_id = 32;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 33 WHERE question_id = 33;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 34 WHERE question_id = 34;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 35 WHERE question_id = 35;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 36 WHERE question_id = 36;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 37 WHERE question_id = 37;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 38 WHERE question_id = 38;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 39 WHERE question_id = 39;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 40 WHERE question_id = 40;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 41 WHERE question_id = 41;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 42 WHERE question_id = 42;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 43 WHERE question_id = 43;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 44 WHERE question_id = 44;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 45 WHERE question_id = 45;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 46 WHERE question_id = 46;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 47 WHERE question_id = 47;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 48 WHERE question_id = 48;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 49 WHERE question_id = 49;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 50 WHERE question_id = 50;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 51 WHERE question_id = 51;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 52 WHERE question_id = 52;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 53 WHERE question_id = 53;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 54 WHERE question_id = 54;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 55 WHERE question_id = 55;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 56 WHERE question_id = 56;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 57 WHERE question_id = 57;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 58 WHERE question_id = 58;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 59 WHERE question_id = 59;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 60 WHERE question_id = 60;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 61 WHERE question_id = 61;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 62 WHERE question_id = 62;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 63 WHERE question_id = 63;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 64 WHERE question_id = 64;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 65 WHERE question_id = 65;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 66 WHERE question_id = 66;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 67 WHERE question_id = 67;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 68 WHERE question_id = 68;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 69 WHERE question_id = 69;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 70 WHERE question_id = 70;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 71 WHERE question_id = 71;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 72 WHERE question_id = 72;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 73 WHERE question_id = 73;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 74 WHERE question_id = 74;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 75 WHERE question_id = 75;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 76 WHERE question_id = 76;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 77 WHERE question_id = 77;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 78 WHERE question_id = 78;
UPDATE question_progress_trackers SET aggregate_question_tracker_id = 79 WHERE question_id = 79;
