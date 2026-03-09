import { connect, credentials } from "amqplib/callback_api.js"

const user_rabbit = process.env.RABBITMQ_USER
const password_rabbit = process.env.RABBITMQ_PASSWORD
const rabbit_host = process.env.RABBITMQ_ADDRESS
const port_rabbit = process.env.RABBITMQ_PORT

const options = { credentials: credentials.plain(user_rabbit, password_rabbit) }

const url = `amqp://${user_rabbit}:${password_rabbit}@${rabbit_host}:${port_rabbit}`

function connectRabbit() {

  console.log("Trying to connect to RabbitMQ...")

  connect(url, options, function (error_one, connection) {

    if (error_one) {
      console.log("RabbitMQ not ready, retrying in 5 seconds...")
      setTimeout(connectRabbit, 5000)
      return
    }

    console.log("Connected to RabbitMQ")

    connection.createChannel(function (error_two, channel) {

      if (error_two) {
        console.log("Channel error:", error_two)
        return
      }

      const queue = "hello"
      const message = "hello world"

      channel.assertQueue(queue, { durable: false })

      channel.sendToQueue(queue, Buffer.from(message))

      console.log("Message sent:", message)
    })

    connection.on("close", () => {
      console.log("RabbitMQ connection closed. Reconnecting...")
      setTimeout(connectRabbit, 5000)
    })
  })
}

connectRabbit()
