package edu.course.lab04;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IssueRegistryTest {

    // --- ВСПОМОГАТЕЛЬНЫЕ КЛАССЫ ДЛЯ ТЕСТОВЫХ ЭКСПЕРИМЕНТОВ ---

    // Ключ с намеренной коллизией: equals работает честно, но hashCode всегда одинаковый
    static final class CollisionIssueId {
        private final String code;

        CollisionIssueId(String code) {
            this.code = code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CollisionIssueId that = (CollisionIssueId) o;
            return code.equals(that.code);
        }

        @Override
        public int hashCode() {
            return 777; // Искусственная коллизия: у всех объектов один хэш-код
        }
    }

    // Специальный учебный изменяемый ключ (только для тестов)
    static final class MutableIssueId {
        private String projectCode; // Внимание: поле не final и есть сеттер

        MutableIssueId(String projectCode) {
            this.projectCode = projectCode;
        }

        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MutableIssueId that = (MutableIssueId) o;
            return projectCode.equals(that.projectCode);
        }

        @Override
        public int hashCode() {
            return projectCode.hashCode();
        }
    }

    // --- ОБЯЗАТЕЛЬНЫЕ ЭКСПЕРИМЕНТЫ И ТЕСТЫ ---

    @Test
    void testBasicOperationsAndLogicalEquality() {
        IssueRegistry registry = new IssueRegistry();

        IssueId key1 = new IssueId("CORE", 101);
        IssueId key2 = new IssueId("CORE", 101); // Логически равен key1
        ProjectTask task = new ProjectTask("id-01", "title 1", 12);

        // 1. Два разных экземпляра логически равного ключа находят одно значение
        registry.put(key1, task);
        assertNotNull(registry.get(key2));
        assertEquals("id-01", registry.get(key2).getId());

        // 2. Повторное добавление с равным ключом заменяет значение и не увеличивает размер
        ProjectTask updatedTask = new ProjectTask("id-02", "title 2", 10);
        registry.put(key2, updatedTask);

        assertEquals(1, registry.size());
        assertEquals("id-02", registry.get(key1).getId());
    }

    @Test
    void testCollisionHandling() {
        // 3. Два неравных ключа с намеренно одинаковым hashCode сохраняют разные значения
        java.util.Map<CollisionIssueId, String> map = new java.util.HashMap<>();

        CollisionIssueId keyA = new CollisionIssueId("PROJ-A");
        CollisionIssueId keyB = new CollisionIssueId("PROJ-B");

        map.put(keyA, "Задача из проекта А");
        map.put(keyB, "Задача из проекта Б");

        // Проверяем, что карта успешно разрешила коллизию (сохранила оба элемента)
        assertEquals(2, map.size());
        assertEquals("Задача из проекта А", map.get(keyA));
        assertEquals("Задача из проекта Б", map.get(keyB));
    }

    @Test
    void testEqualsWithNullAndOtherTypes() {
        // 4. Сравнение с null и объектом другого типа возвращает false
        IssueId key = new IssueId("WEB", 50);

        assertFalse(key.equals(null));
        assertFalse(key.equals("Просто строка вместо ключа"));
        assertFalse(key.equals(Integer.valueOf(50)));
    }

    @Test
    void testMutableKeyProblem() {
        // 5. Проблема изменяемого учебного ключа
        java.util.Map<MutableIssueId, String> map = new java.util.HashMap<>();

        MutableIssueId mutableKey = new MutableIssueId("DEV");
        map.put(mutableKey, "Критический баг");

        // Пока ключ стабилен, значение находится без проблем
        assertEquals("Критический баг", map.get(mutableKey));

        // Ломаем состояние ключа, пока он лежит в HashMap
        mutableKey.setProjectCode("QA");

        // ПОСЛЕДСТВИЯ:
        // Хэш-код изменился, HashMap ищет объект в другом баке и возвращает null
        assertNull(map.get(mutableKey));

        // Объект физически остался внутри структуры, но стал недоступен по сломанному ключу
        assertFalse(map.containsKey(mutableKey));
        assertEquals(1, map.size());
    }
}
