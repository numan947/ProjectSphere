import { Box, Button, Grid, HStack, useDisclosure } from "@chakra-ui/react";
import FilterList from "./FilterList";
import { FaFilter, FaPlus } from "react-icons/fa";
import { TiTags } from "react-icons/ti";
import ProjectCardList from "./ProjectCardList";
import CreateProjectModal from "./CreateProjectModal";
import { useCategories, useTags } from "../hooks/useMetadata";
import { useFetchProjectShortResponses } from "../hooks/useProjects";
import InvitationList from "../../invitation/pages/InvitationList";

const ProjectHome = () => {
  const {
    data: categories,
    refetch: refetchCategories,
    isLoading: categoriesLoading,
  } = useCategories();
  const {
    data: tags,
    refetch: refetchTags,
    isLoading: tagsLoading,
  } = useTags();

  const {
    data: projects,
    refetch: refetchProjects,
    isLoading: projectsLoading,
  } = useFetchProjectShortResponses();

  const { isOpen, onClose, onOpen } = useDisclosure();

  const onCreatedSuccess = () => {
    refetchCategories();
    refetchTags();
    refetchProjects();
    console.log("Project created successfully");
    onClose();
  };
  const onCancelled = () => {
    console.log("Project creation cancelled");
    onClose();
  };
  const onCreatedError = (msg: string) => {
    console.log("Project creation failed" + msg);
    onClose();
  };

  return (
    <Grid
      templateColumns={{
        sm: "1fr",
        md: "1fr 4fr",
      }}
      height="100vh"
      minWidth={{ sm: "100%", md: "80%" }}
    >
      {/* Aside Section - Sticky */}
      <Box
        position="sticky"
        top={0} // Keeps the aside at the top of the viewport
        height="100vh"
        borderRight="1px solid"
        borderColor="gray.200"
        overflowY="auto"
        p={4}
      >
        <HStack mb={4} justifyContent="center">
          <>
            <Button
              leftIcon={<FaPlus />}
              colorScheme="linkedin"
              onClick={onOpen}
            >
              Create Project
            </Button>
            <CreateProjectModal
              isOpen={isOpen}
              onClose={onClose}
              onCancelled={onCancelled}
              onCreatedError={onCreatedError}
              onCreatedSuccess={onCreatedSuccess}
            />
          </>
        </HStack>

        <FilterList
          colorScheme="purple"
          title="Categories"
          filters={categories || []}
          isloading={categoriesLoading}
          orientation="column"
          icon={<FaFilter />}
        />
        <FilterList
          title="Tags"
          colorScheme="blue"
          filters={tags || []}
          isloading={tagsLoading}
          orientation="column"
          icon={<TiTags />}
        />
        <InvitationList />
      </Box>

      {/* Main Section */}
      <Box
        overflowY="auto" // Allows scrolling within the main content
        height="100vh"
        p={4}
      >
        <ProjectCardList
          projects={projects || []}
          isLoading={projectsLoading}
        />
      </Box>
    </Grid>
  );
};

export default ProjectHome;
