import { Box, Button, Grid, HStack, useDisclosure } from "@chakra-ui/react";
import FilterList from "../../../components/FilterList";
import { FaFilter, FaPlus } from "react-icons/fa";
import { TiTags } from "react-icons/ti";
import ProjectCardList from "../../../components/ProjectCardList";
import CreateProjectModal from "./CreateProjectModal";

const ProjectHome = () => {
  const filters = ["All", "Active", "Completed"];
  const tags = ["Urgent", "High Priority", "Low Priority"];

  const { isOpen, onClose, onOpen } = useDisclosure();

  return (
    <Grid
      templateColumns={{
        sm: "1fr",
        md: "1fr 4fr",
      }}
      height="100vh"
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
            <CreateProjectModal isOpen={isOpen} onClose={onClose} />
          </>
        </HStack>

        <FilterList
          title="Categories"
          filters={filters}
          orientation="column"
          icon={<FaFilter />}
        />
        <FilterList
          title="Tags"
          filters={tags}
          orientation="column"
          icon={<TiTags />}
        />
      </Box>

      {/* Main Section */}
      <Box
        overflowY="auto" // Allows scrolling within the main content
        height="100vh"
        p={4}
      >
        <ProjectCardList />
      </Box>
    </Grid>
  );
};

export default ProjectHome;
