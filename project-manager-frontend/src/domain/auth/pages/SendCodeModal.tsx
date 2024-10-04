import {
  Text,
  Button,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalHeader,
  ModalOverlay,
  FormControl,
  FormErrorMessage,
  Input,
  HStack,
} from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { Controller, useForm } from "react-hook-form";
import { z } from "zod";

const SendCodeModalSchema = z.object({
  email: z.string().email("Invalid email address").min(1, "Email is required"),
});
const defaultValues = {
  email: "",
};

interface SendCodeModalProps {
  isOpen: boolean;
  onClose: () => void;
  headerText: string;
  bodyText: string;
  onSubmit?: (data: any) => void;
}

const SendCodeModal = ({
  isOpen,
  onClose,
  headerText,
  bodyText,
  onSubmit,
}: SendCodeModalProps) => {
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(SendCodeModalSchema),
    defaultValues: defaultValues,
  });

  const handleOnClose = () => {
    reset();
    onClose();
  };

  const onSubmitRequest = (data: any) => {
    onSubmit && onSubmit(data.email);
    handleOnClose();
  };

  return (
    <>
      <Modal isOpen={isOpen} onClose={handleOnClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{headerText}</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text pb={3}>{bodyText}</Text>
            <form onSubmit={handleSubmit(onSubmitRequest)}>
              <FormControl isInvalid={!!errors.email}>
                <Controller
                  name="email"
                  control={control}
                  render={({ field }) => (
                    <Input
                      {...field}
                      placeholder="Enter a valid email address"
                    />
                  )}
                />
                <FormErrorMessage>{errors.email?.message}</FormErrorMessage>
              </FormControl>
              <HStack mt={4} pb={4} justifyContent="center">
                <Button
                  colorScheme="green"
                  mr={3}
                  onClick={handleOnClose}
                  variant="outline"
                >
                  Cancel
                </Button>
                <Button variant="outline" colorScheme="blue" type="submit">
                  Submit Request
                </Button>
              </HStack>
            </form>
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  );
};

export default SendCodeModal;
