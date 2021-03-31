package kz.kazgisa.data.repositories.appinfo;

import kz.kazgisa.data.entity.appinfo.ObjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectInfoRepository extends JpaRepository<ObjectInfo, Long> {

    @Query(value = "select st_astext(st_transform(st_setsrid(st_multi(st_geomfromgeojson(cast((cast(location as json)->'geometry') as text))),4326),3857)) from object_info where id=:id", nativeQuery = true)
    String getLocationWkt(@Param("id") Long id);
}
