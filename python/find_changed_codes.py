import os
import io
from pydriller import RepositoryMining, GitRepository
from pydriller.domain.commit import ModificationType
from pprint import pprint
from datetime import datetime

# Enter the repository url to be cloned and mined
git_url = "https://github.com/apache/commons-csv.git"
# Prepare git clone command
cmd = "git clone " + git_url
# Clone the repository to be mined
os.system(cmd)

# Define repository name. The name should be the same with the folder name of the cloned repository
repository_name = "commons-csv"
# Create a unique folder for the mined repository
dir_name = "./"+repository_name+"_data/"

gr = GitRepository(repository_name)

# Define start date for collecting commits
dt1 = datetime(2019, 6, 5, 7, 25, 0)

# Define end date for collecting commits
dt2 = datetime(2020, 2, 6, 11, 43, 0)

# Only find commits that has change in "*.java" extension folders
for commit in RepositoryMining(repository_name, since=dt1, to=dt2, only_modifications_with_file_types=['.java']).traverse_commits():
    # Create a folder under the name of the mined repository and create a 
    if not os.path.exists(dir_name + str(commit.hash)):
        os.makedirs(dir_name + str(commit.hash))
        # Get the modified codes
        for modified_file in commit.modifications:
        # Create folder for codes "before change"
            if not os.path.exists(dir_name + str(commit.hash) + "/before"):
                os.makedirs(dir_name + str(commit.hash) + "/before")
            
            # Only download the java files, ignore the rest
            if modified_file.filename.endswith(".java"):
                # Create the java file in write mode with UTF-8 encoding
                with io.open(dir_name + str(commit.hash) + "/before/" + modified_file.filename, "w", encoding="utf-8") as f:
                    f.write(str(modified_file.source_code_before))
                    f.close
            # Create folder for codes "after change"
            if not os.path.exists(dir_name + str(commit.hash) + "/after"):
                os.makedirs(dir_name + str(commit.hash) + "/after")
            
            # Only download the java files, ignore the rest            
            if modified_file.filename.endswith(".java"):
                # Create the java file in write mode with UTF-8 encoding
                with io.open(dir_name + str(commit.hash) + "/after/" + modified_file.filename, "w", encoding="utf-8") as f:
                    f.write(str(modified_file.source_code))
                    f.close