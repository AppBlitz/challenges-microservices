export default class MessageSendEmail {
  constructor(to_email, name_employee, message) {
    this.to_email = to_email
    this.message = message
    this.name_employee = name_employee
  }

  get name_employee() {
    return this.name_employee
  }

  get to_email() {
    return this.to_email
  }

  get getMessage() {
    return this.message
  }

  set name_employee(name_employee) {
    this.name_employee = name_employee
  }


  set to_email(to_email) {
    this.to_email = to_email
  }

  set message(message) {
    this.message = message
  }


}
