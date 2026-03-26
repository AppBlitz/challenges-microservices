/**
 * @fileoverview
 * This module defines the DeleteEmployee class.
 * It represents an employee entity used in delete operations,
 * encapsulating employee data and providing controlled access
 * through getters and setters.
 */

/**
 * Class representing an employee to be deleted.
 */
export default class DeleteEmployee {
  /**
   * Creates an instance of DeleteEmployee.
   *
   * @constructor
   * @param {number|string} id_employee - Unique identifier of the employee
   * @param {string} name_employee - Name of the employee
   * @param {string} email_employee - Email address of the employee
   */
  constructor(id_employee, name_employee, email_employee) {
    /**
     * Unique identifier of the employee.
     * @type {number|string}
     */
    this.id_employee = id_employee;

    /**
     * Name of the employee.
     * @type {string}
     */
    this.name_employee = name_employee;

    /**
     * Email address of the employee.
     * @type {string}
     */
    this.email_employee = email_employee;
  }

  /**
   * Gets the employee ID.
   *
   * @returns {number|string} The employee ID
   */
  getId() {
    return this.id_employee;
  }

  /**
   * Gets the employee name.
   *
   * @returns {string} The employee name
   */
  getName() {
    return this.name_employee;
  }

  /**
   * Gets the employee email.
   *
   * @returns {string} The employee email
   */
  getEmail() {
    return this.email_employee;
  }

  /**
   * Sets the employee ID.
   *
   * @param {number|string} id_employee - New employee ID
   * @returns {void}
   */
  setId(id_employee) {
    this.id_employee = id_employee;
  }

  /**
   * Sets the employee name.
   *
   * @param {string} name_employee - New employee name
   * @returns {void}
   */
  setName(name_employee) {
    this.name_employee = name_employee;
  }

  /**
   * Sets the employee email.
   *
   * @param {string} email_employee - New employee email
   * @returns {void}
   */
  setEmail(email_employee) {
    this.email_employee = email_employee;
  }
}
