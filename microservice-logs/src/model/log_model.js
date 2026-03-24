export default class Log {
  constructor(id_employee, name_employee, email_employee, department_id, date_enter) {
    this._id_employee = id_employee;
    this._name_employee = name_employee;
    this._email_employee = email_employee;
    this._department_id = department_id;
    this._date_enter = date_enter;
  }

  get id_employee() {
    return this._id_employee;
  }

  get name_employee() {
    return this._name_employee;
  }

  get email_employee() {
    return this._email_employee;
  }

  get department_id() {
    return this._department_id;
  }

  get date_enter() {
    return this._date_enter;
  }

  set id_employee(value) {
    this._id_employee = value;
  }

  set name_employee(value) {
    this._name_employee = value;
  }

  set email_employee(value) {
    this._email_employee = value;
  }

  set department_id(value) {
    this._department_id = value;
  }

  set date_enter(value) {
    this._date_enter = value;
  }


}


