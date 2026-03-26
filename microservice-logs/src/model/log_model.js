/**
 * @fileoverview
 * Defines the Log class, which represents an employee record
 * containing identification, personal information, department,
 * and registration date.
 */

/**
 * Class representing a Log (employee record).
 */
export default class Log {
  /**
   * Creates a new Log instance.
   *
   * @constructor
   * @param {number} id_employee - Unique identifier of the employee
   * @param {string} name_employee - Name of the employee
   * @param {string} email_employee - Email address of the employee
   * @param {number} department_id - Identifier of the department
   * @param {Date} date_enter - Date when the employee was registered
   */
  constructor(
    id_employee,
    name_employee,
    email_employee,
    department_id,
    date_enter
  ) {
    /**
     * Employee ID (private convention).
     * @type {number}
     * @private
     */
    this._id_employee = id_employee;

    /**
     * Employee name (private convention).
     * @type {string}
     * @private
     */
    this._name_employee = name_employee;

    /**
     * Employee email (private convention).
     * @type {string}
     * @private
     */
    this._email_employee = email_employee;

    /**
     * Department ID (private convention).
     * @type {number}
     * @private
     */
    this._department_id = department_id;

    /**
     * Employee registration date (private convention).
     * @type {Date}
     * @private
     */
    this._date_enter = date_enter;
  }

  /**
   * Gets the employee ID.
   *
   * @returns {number} Employee ID
   */
  get id_employee() {
    return this._id_employee;
  }

  /**
   * Gets the employee name.
   *
   * @returns {string} Employee name
   */
  get name_employee() {
    return this._name_employee;
  }

  /**
   * Gets the employee email.
   *
   * @returns {string} Employee email
   */
  get email_employee() {
    return this._email_employee;
  }

  /**
   * Gets the department ID.
   *
   * @returns {number} Department ID
   */
  get department_id() {
    return this._department_id;
  }

  /**
   * Gets the employee registration date.
   *
   * @returns {Date} Registration date
   */
  get date_enter() {
    return this._date_enter;
  }

  /**
   * Sets the employee ID.
   *
   * @param {number} value - New employee ID
   * @returns {void}
   */
  set id_employee(value) {
    this._id_employee = value;
  }

  /**
   * Sets the employee name.
   *
   * @param {string} value - New employee name
   * @returns {void}
   */
  set name_employee(value) {
    this._name_employee = value;
  }

  /**
   * Sets the employee email.
   *
   * @param {string} value - New employee email
   * @returns {void}
   */
  set email_employee(value) {
    this._email_employee = value;
  }

  /**
   * Sets the department ID.
   *
   * @param {number} value - New department ID
   * @returns {void}
   */
  set department_id(value) {
    this._department_id = value;
  }

  /**
   * Sets the employee registration date.
   *
   * @param {Date} value - New registration date
   * @returns {void}
   */
  set date_enter(value) {
    this._date_enter = value;
  }
}
