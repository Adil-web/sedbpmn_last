package kz.kazgisa.data.enums;

public enum StorageType {
	MongoDb,
	GridFS;

	public static StorageType fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("storage type not specified");
        }
        return StorageType.valueOf(value);
	}

	public String getSuffix(){
	    switch (this){
            case MongoDb: return AppConstants.SUFFIX_MONGODB;
            case GridFS: return AppConstants.SUFFIX_GRIDFS;
        }
        throw new IllegalArgumentException("No suffix for: " + this);
    }

    public static StorageType fromFileUid(String uid){
        if(uid.endsWith(AppConstants.SUFFIX_MONGODB)) return MongoDb;
        if(uid.endsWith(AppConstants.SUFFIX_GRIDFS)) return GridFS;
        return null;
    }

    public static StorageType byFileSize(long fileSizeInBytes){
        return fileSizeInBytes > AppConstants.GRIDFS_THRESHOLD
            ? StorageType.GridFS
            : StorageType.MongoDb;
    }
}
