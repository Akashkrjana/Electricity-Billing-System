# ⚡ Electricity Billing System

A Java Swing based desktop application for managing electricity billing operations such as customer registration, bill calculation, payment tracking, and transaction receipt generation.

---

# 📌 Features

- 🔐 User Login & Signup
- 👤 Add New Customer
- 🧾 Generate Electricity Bill
- 💳 Pay Electricity Bill
- 📄 Transaction Receipt Generation
- 📊 View Customer Details
- 🧮 Automatic Bill Calculation
- 🗂 Deposit & Payment Details
- 🖨 Print Bills & Receipts
- 🎨 Java Swing GUI Interface
- 🛢 MySQL Database Integration

---

# 🛠 Technologies Used

| Technology | Description |
|------------|-------------|
| Java | Core Programming Language |
| Java Swing | GUI Development |
| JDBC | Database Connectivity |
| MySQL | Database Management |
| Git & GitHub | Version Control |

---

# 📂 Project Structure

```text
Electricity Billing System
│
├── src/
│   ├── electricity/billing/system/
│   └── icon/
│
├── lib/
│
├── README.md
└── .gitignore
```

---

# 🗄 Database Setup

Create database in MySQL:

```sql
create database ebs;
use ebs;
```

Create required tables:

```sql
create table login(
    meter_no varchar(20),
    username varchar(30),
    name varchar(30),
    password varchar(20),
    user varchar(20)
);

create table customer(
    name varchar(20),
    meter_no varchar(20),
    address varchar(50),
    city varchar(30),
    state varchar(30),
    email varchar(40),
    phone varchar(10)
);

create table meter_info(
    meter_no varchar(20),
    meter_location varchar(20),
    meter_type varchar(20),
    phase_code varchar(20),
    bill_type varchar(20),
    days varchar(20)
);

create table tax(
    cost_per_unit varchar(20),
    meter_rent varchar(20),
    service_charge varchar(20),
    service_tax varchar(20),
    swacch_bharat_cess varchar(20),
    fixed_tax varchar(20)
);

insert into tax values(
    '9','47','22','57','6','18'
);

create table bill(
    meter_no varchar(20),
    month varchar(30),
    units varchar(20),
    totalbill varchar(20),
    status varchar(20),
    payment_date varchar(50),
    transaction_id varchar(50)
);
```

---

# ▶ How To Run

1. Clone Repository

```bash
git clone https://github.com/Akashkrjana/Electricity-Billing-System.git
```

2. Open Project in VS Code or IntelliJ IDEA

3. Add MySQL JDBC Driver

4. Setup MySQL Database

5. Run:

```text
Project.java
```

---

# 📸 Screenshots

Add your project screenshots here later.

Example:

```markdown
![Login Page](screenshots/login.png)
```

---

# 🚀 Future Improvements

- PDF Bill Generation
- Email Notifications
- Online Payment Gateway
- Admin Dashboard
- Data Analytics Charts
- Password Encryption
- Better UI Design

---

# 👨‍💻 Author

**Akash Kumar Jana**

GitHub:  
https://github.com/Akashkrjana

---

# ⭐ Support

If you like this project, give it a ⭐ on GitHub.
