// service_logs.test.js
import { jest, describe, test, expect, afterEach } from "@jest/globals";
// ✅ Con ESM (--experimental-vm-modules) se usa unstable_mockModule en lugar de jest.mock()
await jest.unstable_mockModule("mongoose", () => ({
  default: {
    connect: jest.fn().mockResolvedValue(true),
    Schema: jest.fn().mockImplementation(() => ({})),
    model: jest.fn().mockReturnValue({}),
  },
}));

await jest.unstable_mockModule("../src/repositories/mongo_db.js", () => ({
  save_employee_save_logs: jest.fn(),
  search_all_logs: jest.fn(),
}));

// ✅ Los imports deben ser dinámicos y DESPUÉS de los mocks
const { insert_log_save_employee, search_log } = await import("../src/services/service_logs.js");
const mongoRepo = await import("../src/repositories/mongo_db.js");

// ─────────────────────────────────────────────
// Helpers
// ─────────────────────────────────────────────
const buildLogData = (overrides = {}) => ({
  ID_employee: 1,
  name_employee: "John Doe",
  email_employee: "john.doe@mail.com",
  department_id: 10,
  date_enter: new Date("2024-01-15T10:00:00Z"),
  ...overrides,
});

// ─────────────────────────────────────────────
// Tests
// ─────────────────────────────────────────────
describe("Log Service", () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  // ── insert_log_save_employee ──────────────────
  describe("insert_log_save_employee", () => {
    test("llama a save_employee_save_logs exactamente una vez", () => {
      insert_log_save_employee(buildLogData());

      expect(mongoRepo.save_employee_save_logs).toHaveBeenCalledTimes(1);
    });

    test("pasa el objeto completo sin modificarlo al repositorio", () => {
      const logData = buildLogData();

      insert_log_save_employee(logData);

      expect(mongoRepo.save_employee_save_logs).toHaveBeenCalledWith(logData);
    });

    test("funciona con campos mínimos", () => {
      const minimal = { ID_employee: 99 };

      insert_log_save_employee(minimal);

      expect(mongoRepo.save_employee_save_logs).toHaveBeenCalledWith(minimal);
    });

    test("no llama a search_all_logs", () => {
      insert_log_save_employee(buildLogData());

      expect(mongoRepo.search_all_logs).not.toHaveBeenCalled();
    });
  });

  // ── search_log ────────────────────────────────
  describe("search_log", () => {
    test("llama a search_all_logs exactamente una vez", () => {
      mongoRepo.search_all_logs.mockReturnValue([]);

      search_log();

      expect(mongoRepo.search_all_logs).toHaveBeenCalledTimes(1);
    });

    test("retorna la lista de logs que devuelve el repositorio", () => {
      const fakeLogs = [
        buildLogData({ ID_employee: 1, name_employee: "John Doe" }),
        buildLogData({ ID_employee: 2, name_employee: "Jane Smith" }),
      ];
      mongoRepo.search_all_logs.mockReturnValue(fakeLogs);

      const result = search_log();

      expect(result).toEqual(fakeLogs);
    });

    test("retorna array vacío si el repositorio no tiene registros", () => {
      mongoRepo.search_all_logs.mockReturnValue([]);

      const result = search_log();

      expect(result).toEqual([]);
    });

    test("retorna undefined si el repositorio devuelve undefined", () => {
      mongoRepo.search_all_logs.mockReturnValue(undefined);

      const result = search_log();

      expect(result).toBeUndefined();
    });

    test("no llama a save_employee_save_logs", () => {
      mongoRepo.search_all_logs.mockReturnValue([]);

      search_log();

      expect(mongoRepo.save_employee_save_logs).not.toHaveBeenCalled();
    });
  });
});