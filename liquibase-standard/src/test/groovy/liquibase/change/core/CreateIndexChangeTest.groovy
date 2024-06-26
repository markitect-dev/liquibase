package liquibase.change.core

import liquibase.change.AddColumnConfig
import liquibase.change.ChangeStatus;
import liquibase.change.StandardChangeTest
import liquibase.database.core.MockDatabase
import liquibase.snapshot.MockSnapshotGeneratorFactory
import liquibase.snapshot.SnapshotGeneratorFactory
import liquibase.structure.core.Column
import liquibase.structure.core.Index

public class CreateIndexChangeTest extends StandardChangeTest {
    def getConfirmationMessage() throws Exception {
        when:
        CreateIndexChange refactoring = new CreateIndexChange();
        refactoring.setIndexName("IDX_TEST");

        then:
        "Index IDX_TEST created" == refactoring.getConfirmationMessage()
    }

    def "checkStatus"() {
        when:
        def database = new MockDatabase()
        def snapshotFactory = new MockSnapshotGeneratorFactory()
        SnapshotGeneratorFactory.instance = snapshotFactory

        def index = new Index("idx_test", null, null, "test_table", new Column("test_col"))
        index.unique = true

        def change = new CreateIndexChange()
        change.indexName = index.name
        change.tableName = index.relation.name
        change.columns = [new AddColumnConfig().setName("test_col")]

        then:
        assert change.checkStatus(database).status == ChangeStatus.Status.notApplied

        when: "index created"
        snapshotFactory.addObjects(index)
        then:
        assert change.checkStatus(database).status == ChangeStatus.Status.complete

        when: "we care about unique and they do not match"
        change.unique = false;
        then:
        assert change.checkStatus(database).status == ChangeStatus.Status.incorrect

        when: "we care about unique and they match"
        change.unique = true;
        then:
        assert change.checkStatus(database).status == ChangeStatus.Status.complete

    }

    def "validationErrorOnEmptyColumn"(){
        when:
        def database = new MockDatabase()
        def snapshotFactory = new MockSnapshotGeneratorFactory()
        SnapshotGeneratorFactory.instance = snapshotFactory

        def index = new Index("idx_test", null, null, "test_table", new Column("test_col"))

        def change = new CreateIndexChange()
        change.indexName = index.name
        change.tableName = index.relation.name
        change.columns = [new AddColumnConfig().setName(null)]

        then:
        assert change.validate(database).getErrorMessages().size() == 1
    }

    def "validate associatedWith is not computed on CheckSum generation"() {
        when:
        def index = new Index("idx_test", null, null, "test_table", new Column("test_col"))
        index.unique = true
        def originalChange = new CreateIndexChange()
        originalChange.indexName = index.name
        originalChange.tableName = index.relation.name
        originalChange.columns = [new AddColumnConfig().setName("test_col")]
        def originalChangeCheckSum = originalChange.generateCheckSum()

        and:
        def changeWithAssociatedWithSet = new CreateIndexChange()
        changeWithAssociatedWithSet.indexName = index.name
        changeWithAssociatedWithSet.tableName = index.relation.name
        changeWithAssociatedWithSet.associatedWith = "foreignKey"
        changeWithAssociatedWithSet.columns = [new AddColumnConfig().setName("test_col")]
        def changeWithAssociatedWithSetCheckSum = changeWithAssociatedWithSet.generateCheckSum()

        then:
        originalChangeCheckSum == changeWithAssociatedWithSetCheckSum
    }

    def "validate create index CheckSum value is not the same if any other property is added"() {
        when:
        def index = new Index("idx_test", null, null, "test_table", new Column("test_col"))
        index.unique = true
        def originalChange = new CreateIndexChange()
        originalChange.indexName = index.name
        originalChange.tableName = index.relation.name
        originalChange.columns = [new AddColumnConfig().setName("test_col")]
        def originalChangeCheckSum = originalChange.generateCheckSum()

        and:
        def changeWithAssociatedWithSet = new CreateIndexChange()
        changeWithAssociatedWithSet.indexName = index.name
        changeWithAssociatedWithSet.tableName = index.relation.name
        changeWithAssociatedWithSet.associatedWith = "foreignKey"
        changeWithAssociatedWithSet.schemaName = "testSchema"
        changeWithAssociatedWithSet.columns = [new AddColumnConfig().setName("test_col")]
        def changeWithAssociatedWithSetCheckSum = changeWithAssociatedWithSet.generateCheckSum()

        then:
        originalChangeCheckSum != changeWithAssociatedWithSetCheckSum
    }
}
