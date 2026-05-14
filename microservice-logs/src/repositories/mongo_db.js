// service_logs.test.js

// ✅ DEBE ir antes de cualquier import que use mongoose
jest.mock("mongoose", () => {
  const mockModel = {
    create: jest.fn(),
    find: jest.fn().mockReturnValue({ exec: jest.fn() }),
    findOne: jest.fn(),
    save: jest.fn(),
  };

  const mockSchema = jest.fn().mockImplementation(() => ({}));

  return {
    connect: jest.fn().mockResolvedValue(true),   // 👈 evita el crash del URI
    Schema: mockSchema,
    model: jest.fn().mockReturnValue(mockModel),
  };
});

// Mockear las funciones del repositorio
jest.mock("../src/repositories/mongo_db.js", () => ({
  save_employee_save_logs: jest.fn(),
  search_all_logs: jest.fn(),
}));

import { insert_log_save_employee, search_log } from "../src/services/service_logs.js";
import * as mongoRepo from "../src/repositories/mongo_db.js";

describe("Log Service", () => {
  beforeAll(() => {
    jest.spyOn(console, "log").mockImplementation(() => {});
    jest.spyOn(console, "error").mockImplementation(() => {});
  });

  afterAll(() => {
    console.log.mockRestore();
    console.error.mockRestore();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe("insert_log_save_employee", () => {
    test("debe llamar a save_employee_save_logs con el objeto correcto", () => {
      const logData = {
        ID_employee: 1,
        name_employee: "John Doe",
        email_employee: "john.doe@mail.com",
        department_id: 10,
        date_enter: new Date(),
      };

      insert_log_save_employee(logData);

      expect(mongoRepo.save_employee_save_logs).toHaveBeenCalledTimes(1);
      expect(mongoRepo.save_employee_save_logs).toHaveBeenCalledWith(logData);
    });
  });

  describe("search_log", () => {
    test("debe retornar lo que devuelve search_all_logs", () => {
      const fakeLogs = [
        { ID_employee: 1, name_employee: "John Doe" },
        { ID_employee: 2, name_employee: "Jane Smith" },
      ];

      mongoRepo.search_all_logs.mockReturnValue(fakeLogs);

      const result = search_log();

      expect(mongoRepo.search_all_logs).toHaveBeenCalledTimes(1);
      expect(result).toEqual(fakeLogs);
    });

    test("debe retornar array vacío si no hay logs", () => {
      mongoRepo.search_all_logs.mockReturnValue([]);
      const result = search_log();
      expect(result).toEqual([]);
    });
  });
});