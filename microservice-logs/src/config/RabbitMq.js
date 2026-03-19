import { connect, credentials } from "amqplib"

const user_rabbit = process.env.RABBITMQ_USER
const password_rabbit = process.env.RABBITMQ_PASSWORD
const rabbit_host = process.env.RABBITMQ_ADDRESS
const port_rabbit = process.env.RABBITMQ_PORT
const exchange_rabbit = process.env.EXCHANGE_RABBIT
const event_one = process.env.EVENT_ONE
const event_two = process.env.EVENT_TWO
const rabbitmq_name = process.env.RABBITMQ_NAME

const options = { credentials: credentials.plain(user_rabbit, password_rabbit) }
const url = `amqp://${rabbit_host}:${port_rabbit}`

async function connectionRabbitMq() {
  let attempts = 10;

  while (attempts > 0) {
    try {
      const connection = await connect(url, options);
      const channel = await connection.createChannel();

      await channel.assertExchange(exchange_rabbit, "direct", {
        durable: false
      });

      const queue = await channel.assertQueue(rabbitmq_name, {
        durable: false
      });

      await channel.bindQueue(queue.queue, exchange_rabbit, event_one);
      await channel.bindQueue(queue.queue, exchange_rabbit, event_two);

      channel.consume(queue.queue, (message) => {
        if (message !== null) {
          const routingKey = message.fields.routingKey;

          switch (routingKey) {
            case event_one:
              console.log(message.content.toString());
              break;

            case event_two:
              console.log(message.content.toString());
              break;

            default:
              break;
          }
        }
      }, { noAck: true });

      return;

    } catch (error) {
      attempts--;

      if (attempts === 0) {
        return;
      }

      await sleep(5000);
    }
  }
}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}
export { connectionRabbitMq }

