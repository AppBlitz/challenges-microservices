import { createTransport } from "nodemailer"
import fs from "fs";
import path from "path";


const html = fs.readFileSync(
  path.join(process.cwd(), "send.html"),
  "utf-8"
);
const trasnport = createTransport(options, {
  host: "smtp.gmail.com",
  port: 587,
  secure: true,
  auth: {
    user: process.env.USER_SMTP,
    pass: process.env.PASS_SMTP
  }
})

async function send_message(MessageSendEmail) {
  try {
    const info = await trasnport.sendMail({
      from: process.env.EMAIL_TO,
      to: MessageSendEmail.EMAIL_TO,
      html: html.replace("{{name}}", MessageSendEmail.name_employee)
    })
  } catch (error) {
    console.error(error);
  }
}
