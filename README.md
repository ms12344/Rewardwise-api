RewardWise API

📌 Overview

RewardWise API calculates customer reward points based on transactions.

🧠 Reward Rules

- 2 points for every $1 spent above $100
- 1 point for every $1 spent between $50–$100

🚀 Tech Stack

- Java 17
- Spring Boot
- H2 Database
- JUnit & Mockito

📡 API Endpoint

POST "/rewardwise/api/rewards"

Sample Request

[
  { "customerId": "C1", "amount": 120, "date": "2026-01-10" }
]

Sample Response

[
  {
    "customerId": "C1",
    "monthlyPoints": {
      "JANUARY": 90
    },
    "totalPoints": 90
  }
]

🧪 Running Tests

mvn test

⚙️ Run Application

mvn spring-boot:run

📊 H2 Console

http://localhost:8080/rewardwise/h2-console

💡 Features

- Monthly & total reward calculation
- Configurable reward rules
- Exception handling
- Unit & integration tests

📈 Future Enhancements

- Database persistence
- Caching
- Swagger documentation