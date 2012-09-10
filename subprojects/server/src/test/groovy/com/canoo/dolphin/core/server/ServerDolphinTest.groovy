package com.canoo.dolphin.core.server

public class ServerDolphinTest extends GroovyTestCase{

    ServerDolphin dolphin

    @Override
    protected void setUp() throws Exception {
        dolphin = new ServerDolphin()
    }

    // todo dk: creating a SPM adds the respective commands to the response

    void testPutToPmAndFindData() {
        def pm = new ServerPresentationModel("uniqueId", [])
        assert null    == dolphin.putData(pm, "key", "value")
        assert "value" == dolphin.findData(pm, "key")
        assert "value" == dolphin.putData(pm, "key", "otherValueForSameKey")
        assert "otherValueForSameKey" == dolphin.findData(pm, "key")
        dolphin.putData(pm, "key2", "value2")
        assert ["key", "key2"] == dolphin.getDataKeys(pm)
        assert "value2" == dolphin.removeData(pm, "key2")
        assert "otherValueForSameKey" == dolphin.removeData(pm, "key")
        assert [] == dolphin.getDataKeys(pm)
    }

    void testPutToAttributeAndFindData() {
        def att = new ServerAttribute("propName")
        assert null    == dolphin.putData(att, "key", "value")
        assert "value" == dolphin.findData(att, "key")
        assert "value" == dolphin.putData(att, "key", "otherValueForSameKey")
        assert "otherValueForSameKey" == dolphin.findData(att, "key")
        dolphin.putData(att, "key2", "value2")
        assert ["key", "key2"] == dolphin.getDataKeys(att)
        assert "value2" == dolphin.removeData(att, "key2")
        assert "otherValueForSameKey" == dolphin.removeData(att, "key")
        assert [] == dolphin.getDataKeys(att)
    }

    void testListPresentationModels() {
        assert dolphin.listPresentationModelIds().empty
        assert dolphin.listPresentationModels().empty

        def pm1 = new ServerPresentationModel("first", [])
        dolphin.modelStore.add pm1

        assert ['first'].toSet() == dolphin.listPresentationModelIds()
        assert [pm1]             == dolphin.listPresentationModels().toList()

        def pm2 = new ServerPresentationModel("second", [])
        dolphin.modelStore.add pm2

        assert 2 == dolphin.listPresentationModelIds().size()
        assert 2 == dolphin.listPresentationModels().size()

        for (id in dolphin.listPresentationModelIds()){
            assert dolphin.findPresentationModelById(id) in dolphin.listPresentationModels()
        }
    }

}