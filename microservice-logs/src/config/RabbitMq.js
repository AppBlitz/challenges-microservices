import { connect, credentials } from "amqplib"

const user_rabbit = process.env.RABBITMQ_USER
const password_rabbit = process.env.RABBITMQ_PASSWORD
const rabbit_host = process.env.RABBITMQ_ADDRESS
const port_rabbit = process.env.RABBITMQ_PORT
const exchange_rabbit = process.env.EXCHANGE_RABBIT
const event_one = process.env.EVENT_ONE
const event_two = process.env.EVENT_TWO

const options = { credentials: credentials.plain(user_rabbit, password_rabbit) }
const url = `amqp://${rabbit_host}:${port_rabbit}`

async function connectionRabbitMq() {
  try {
    const connection = await connect(url, options);
    const channel = await connection.createChannel();
    await channel.assertExchange(exchange_rabbit, "direct", {
      durable: false
    });
    const auxiliary_queue = await channel.assertQueue('', {
      exclusive: true
    })
    channel.bindQueue("employee", exchange_rabbit, event_one)
    channel.bindQueue("employee", exchange_rabbit, event_two)
    channel.consume(auxiliary_queue.queue, function (message) {
      if (message !== "null") {
        const routikey = message.fields.routingKey;
      }
    }, {
      noAck: true
    }
    )
  } catch (error) {
    console.error(error)
  }
}

export { connectionRabbitMq }
